package com.bignerdranch.travelcommunity.videocache;


/**
 * Indicates interruption error in work of {@link com.bignerdranch.travelcommunity.videocache.ProxyCache} fired by user.
 *
 * @author Alexey Danilov
 */
public class InterruptedProxyCacheException extends ProxyCacheException {

    public InterruptedProxyCacheException(String message) {
        super(message);
    }

    public InterruptedProxyCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterruptedProxyCacheException(Throwable cause) {
        super(cause);
    }
}
