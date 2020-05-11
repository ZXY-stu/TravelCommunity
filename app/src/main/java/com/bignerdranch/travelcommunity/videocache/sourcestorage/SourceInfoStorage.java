package com.bignerdranch.travelcommunity.videocache.sourcestorage;

import com.bignerdranch.travelcommunity.videocache.SourceInfo;

/**
 * Storage for {@link SourceInfo}.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public interface SourceInfoStorage {

    SourceInfo get(String url);

    void put(String url, SourceInfo sourceInfo);

    void release();
}
