package com.bignerdranch.travelcommunity.videocache;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.bignerdranch.tclib.LogUtil;
import com.bignerdranch.travelcommunity.videocache.file.FileCache;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bignerdranch.travelcommunity.videocache.Preconditions.checkNotNull;

/**
 * Client for {@link HttpProxyCacheServer}
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
final class HttpProxyCacheServerClients {

    private final AtomicInteger clientsCount = new AtomicInteger(0);
    private final String url;
    private volatile com.bignerdranch.travelcommunity.videocache.HttpProxyCache proxyCache;
    private final List<com.bignerdranch.travelcommunity.videocache.CacheListener> listeners = new CopyOnWriteArrayList<>();
    private final com.bignerdranch.travelcommunity.videocache.CacheListener uiCacheListener;
    private final com.bignerdranch.travelcommunity.videocache.Config config;

    public HttpProxyCacheServerClients(String url, com.bignerdranch.travelcommunity.videocache.Config config) {
        this.url = checkNotNull(url);
        this.config = checkNotNull(config);
       LogUtil.INSTANCE.ee("createClinet" + url);
        this.uiCacheListener = new UiListenerHandler(url, listeners);
    }

    public void processRequest(com.bignerdranch.travelcommunity.videocache.GetRequest request, Socket socket) throws com.bignerdranch.travelcommunity.videocache.ProxyCacheException, IOException {
        startProcessRequest();
       // LogUtil.INSTANCE.ee("执行ClientProcessRequest");
        try {
            clientsCount.incrementAndGet();
            proxyCache.processRequest(request, socket);
        } finally {
         if(proxyCache.finished) {
             finishProcessRequest();
             LogUtil.INSTANCE.ee("finishProcessRequest");
         }
        }
    }

    private synchronized void startProcessRequest() throws com.bignerdranch.travelcommunity.videocache.ProxyCacheException {
        proxyCache = proxyCache == null ? newHttpProxyCache() : proxyCache;
    }



    private synchronized void finishProcessRequest() {
        if (clientsCount.decrementAndGet() <= 0) {
            proxyCache.shutdown();
            proxyCache = null;
        }
    }


    public void  tryLoad(){
        LogUtil.INSTANCE.ee("tryLoad" + proxyCache);
       if (proxyCache != null) proxyCache.tryLoad();
    }

    public void canLoad(){
        if (proxyCache != null)  proxyCache.canLoad();
    }

    public void stopLoad(){
        if (proxyCache != null)  proxyCache.stopLoad();
    }

    public void registerCacheListener(com.bignerdranch.travelcommunity.videocache.CacheListener cacheListener) {
        listeners.add(cacheListener);
    }

    public void unregisterCacheListener(com.bignerdranch.travelcommunity.videocache.CacheListener cacheListener) {
        listeners.remove(cacheListener);
    }

    public void shutdown() {
        listeners.clear();
        if (proxyCache != null) {
            proxyCache.registerCacheListener(null);
            proxyCache.shutdown();
            proxyCache = null;
        }
        clientsCount.set(0);
    }







    public int getClientsCount() {
        return clientsCount.get();
    }

    private HttpProxyCache newHttpProxyCache() throws ProxyCacheException {
       HttpUrlSource source = new HttpUrlSource(url, config.sourceInfoStorage, config.headerInjector);
        FileCache cache = new FileCache(config.generateCacheFile(url), config.diskUsage);
       HttpProxyCache httpProxyCache = new HttpProxyCache(source, cache);
        httpProxyCache.registerCacheListener(uiCacheListener);
        return httpProxyCache;
    }

    private static final class UiListenerHandler extends Handler implements CacheListener {

        private final String url;
        private final List<CacheListener> listeners;

        public UiListenerHandler(String url, List<com.bignerdranch.travelcommunity.videocache.CacheListener> listeners) {
            super(Looper.getMainLooper());
            this.url = url;
            this.listeners = listeners;
        }

        @Override
        public void onCacheAvailable(File file, String url, int percentsAvailable) {
            Message message = obtainMessage();
            message.arg1 = percentsAvailable;
            message.obj = file;
            sendMessage(message);
        }

        @Override
        public void handleMessage(Message msg) {
            for (CacheListener cacheListener : listeners) {
                LogUtil.INSTANCE.ee("发起请求...");
                cacheListener.onCacheAvailable((File) msg.obj, url, msg.arg1);
            }
        }
    }
}
