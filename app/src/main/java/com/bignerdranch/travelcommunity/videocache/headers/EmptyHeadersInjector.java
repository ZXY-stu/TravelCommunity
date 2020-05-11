package com.bignerdranch.travelcommunity.videocache.headers;

import com.bignerdranch.travelcommunity.videocache.headers.HeaderInjector;

import java.util.HashMap;
import java.util.Map;

/**
 * Empty {@link com.bignerdranch.travelcommunity.videocache.headers.HeaderInjector} implementation.
 *
 * @author Lucas Nelaupe (https://github.com/lucas34).
 */
public class EmptyHeadersInjector implements HeaderInjector {

    @Override
    public Map<String, String> addHeaders(String url) {
        return new HashMap<>();
    }

}
