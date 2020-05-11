package com.bignerdranch.travelcommunity.videocache.sourcestorage;

import android.content.Context;

import com.bignerdranch.travelcommunity.videocache.sourcestorage.DatabaseSourceInfoStorage;
import com.bignerdranch.travelcommunity.videocache.sourcestorage.NoSourceInfoStorage;
import com.bignerdranch.travelcommunity.videocache.sourcestorage.SourceInfoStorage;

/**
 * Simple factory for {@link com.bignerdranch.travelcommunity.videocache.sourcestorage.SourceInfoStorage}.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public class SourceInfoStorageFactory {

    public static com.bignerdranch.travelcommunity.videocache.sourcestorage.SourceInfoStorage newSourceInfoStorage(Context context) {
        return new DatabaseSourceInfoStorage(context);
    }

    public static SourceInfoStorage newEmptySourceInfoStorage() {
        return new NoSourceInfoStorage();
    }
}
