package com.bignerdranch.travelcommunity.videocache;


import com.bignerdranch.tclib.LogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static com.bignerdranch.travelcommunity.videocache.Preconditions.checkNotNull;

/**
 * Proxy for {@link com.bignerdranch.travelcommunity.videocache.Source} with caching support ({@link com.bignerdranch.travelcommunity.videocache.Cache}).
 * <p/>
 * Can be used only for sources with persistent data (that doesn't change with time).
 * Method {@link #read(byte[], long, int)} will be blocked while fetching data from source.
 * Useful for streaming something with caching e.g. streaming video/audio etc.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class ProxyCache {

    private static final Logger LOG = LoggerFactory.getLogger("ProxyCache");
    private static final int MAX_READ_SOURCE_ATTEMPTS = 1;
    private long MAX_PREPARE_CACHE_SIZE = 1 * 8 * 1024 * 1024;  //预加载2M
    private final com.bignerdranch.travelcommunity.videocache.Source source;
    private final com.bignerdranch.travelcommunity.videocache.Cache cache;
    private final Object wc = new Object();
    private final Object stopLock = new Object();
    private final Object changeLock = new Object();   //暂停锁
    private final Object continueLock = new Object();
    private final AtomicInteger readSourceErrorsCount;
    private volatile Thread sourceReaderThread;
    private volatile boolean stopped;
    private volatile  boolean paused;
    private volatile  boolean continued = false;
    private volatile int percentsAvailable = -1;
    private long currentOffset = 0;
    private int id;  //caseId

    public ProxyCache(Source source, Cache cache) {
        this.source = checkNotNull(source);
        this.cache = checkNotNull(cache);
        this.readSourceErrorsCount = new AtomicInteger();
    }

    public int read(byte[] buffer, long offset, int length) throws ProxyCacheException {
        ProxyCacheUtils.assertBuffer(buffer, offset, length);

        while (!cache.isCompleted() && cache.available() < (offset + length) && !stopped) {
            readSourceAsync();
            waitForSourceData();
            checkReadSourceErrorsCount();

        }
        int read = cache.read(buffer, offset, length);
        if (cache.isCompleted() && percentsAvailable != 100) {
            percentsAvailable = 100;
            onCachePercentsAvailableChanged(100);
        }
        return read;
    }

    private void checkReadSourceErrorsCount() throws ProxyCacheException {
        int errorsCount = readSourceErrorsCount.get();
        if (errorsCount >= MAX_READ_SOURCE_ATTEMPTS) {
            readSourceErrorsCount.set(0);
            throw new ProxyCacheException("Error reading source " + errorsCount + " times");
        }
    }

    public void shutdown() {
        synchronized (stopLock) {
            LOG.debug("Shutdown proxy for " + source);
            try {
                stopped = true;
                if (sourceReaderThread != null) {
                    sourceReaderThread.interrupt();

                }
                cache.close();
            } catch (ProxyCacheException e) {
                onError(e);
            }
        }
    }




    private synchronized void readSourceAsync() throws ProxyCacheException {
        boolean readingInProgress = sourceReaderThread != null && sourceReaderThread.getState() != Thread.State.TERMINATED;
        if (!stopped && !cache.isCompleted() && !readingInProgress) {
            sourceReaderThread = new Thread(new SourceReaderRunnable(), "Source reader for " + source);
            sourceReaderThread.start();
        }
    }

    private void waitForSourceData() throws ProxyCacheException {
        synchronized (wc) {
            try {
                wc.wait(1000);
            } catch (InterruptedException e) {
                throw new ProxyCacheException("Waiting source data is interrupted!", e);
            }
        }
    }

    private void notifyNewCacheDataAvailable(long cacheAvailable, long sourceAvailable) {
        onCacheAvailable(cacheAvailable, sourceAvailable);

        synchronized (wc) {
            wc.notifyAll();
        }
    }

    protected void onCacheAvailable(long cacheAvailable, long sourceLength) {
        boolean zeroLengthSource = sourceLength == 0;
        int percents = zeroLengthSource ? 100 : (int) ((float) cacheAvailable / sourceLength * 100);
        boolean percentsChanged = percents != percentsAvailable;
        boolean sourceLengthKnown = sourceLength >= 0;
        if (sourceLengthKnown && percentsChanged) {
            onCachePercentsAvailableChanged(percents);
        }
        percentsAvailable = percents;
    }

    protected void onCachePercentsAvailableChanged(int percentsAvailable) {
    }


    public  void tryLoad(){
       if(currentOffset>MAX_PREPARE_CACHE_SIZE && sourceReaderThread.isInterrupted()){
             synchronized (changeLock){
                 LogUtil.INSTANCE.eee("继续下载....................." + source);
                 sourceReaderThread.interrupt();
             }
       }
      // LogUtil.INSTANCE.eee("currentOffset"+currentOffset +"Thread.currentThread().isInterrupted()" +sourceReaderThread.isInterrupted());
        continued = true;
    }

    public void pause()  {   //中断任务
        if(sourceReaderThread != null){
            synchronized (changeLock) {
                LogUtil.INSTANCE.eee("停止下载....................." + source);
                continued = false;
                sourceReaderThread.interrupt();
            }
        }
    }

    public void stopLoad(){
        synchronized (changeLock){
            pause();
        }
    }



    public void canLoad(){
        synchronized (changeLock){
            continued = true;
        }
    }


    private void readSource() {
        long sourceAvailable = -1;
        long offset = 0;

        try {
            offset = cache.available();
            source.open(offset);
            sourceAvailable = source.length();
            byte[] buffer = new byte[ProxyCacheUtils.DEFAULT_BUFFER_SIZE];
            int readBytes = 0;
            while ((readBytes = source.read(buffer))!=-1) {
                   synchronized (stopLock) {
                       if (isStopped()) {
                           return;
                       }
                       cache.append(buffer, readBytes);
                   }
                   offset += readBytes;
                   notifyNewCacheDataAvailable(offset, sourceAvailable);
                //   LogUtil.INSTANCE.eee("offset " + offset + "from" + source);
                   currentOffset = offset;
                  if(offset>MAX_PREPARE_CACHE_SIZE &&  !continued) pause();
            }

            tryComplete();
            onSourceRead();
        } catch (Throwable e) {
            readSourceErrorsCount.incrementAndGet();
            onError(e);
        } finally {
            closeSource();
            notifyNewCacheDataAvailable(offset, sourceAvailable);
        }
    }

    private void onSourceRead() {
        // guaranteed notify listeners after source read and cache completed
        percentsAvailable = 100;
        onCachePercentsAvailableChanged(percentsAvailable);
    }

    private void tryComplete() throws ProxyCacheException {
        synchronized (stopLock) {
            if (!isStopped() && cache.available() == source.length()) {
                cache.complete();
            }
        }
    }

    private boolean isStopped() {
        return Thread.currentThread().isInterrupted() || stopped;
    }

    private void closeSource() {
        try {
            source.close();
        } catch (ProxyCacheException e) {
            onError(new ProxyCacheException("Error closing source " + source, e));
        }
    }

    protected final void onError(final Throwable e) {
        boolean interruption = e instanceof InterruptedProxyCacheException;
        if (interruption) {
            LOG.debug("ProxyCache is interrupted");
        } else {
            LOG.error("ProxyCache error", e);
        }
    }

    private class SourceReaderRunnable implements Runnable {

        @Override
        public void run() {
            readSource();
        }
    }
}
