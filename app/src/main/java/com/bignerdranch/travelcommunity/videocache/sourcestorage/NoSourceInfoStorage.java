package com.bignerdranch.travelcommunity.videocache.sourcestorage;

import com.bignerdranch.travelcommunity.videocache.SourceInfo;
import com.bignerdranch.travelcommunity.videocache.sourcestorage.SourceInfoStorage;

/**
 * {@link com.bignerdranch.travelcommunity.videocache.sourcestorage.SourceInfoStorage} that does nothing.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public class NoSourceInfoStorage implements SourceInfoStorage {

    @Override
    public SourceInfo get(String url) {
        return null;
    }

    @Override
    public void put(String url, SourceInfo sourceInfo) {
    }

    @Override
    public void release() {
    }
}
