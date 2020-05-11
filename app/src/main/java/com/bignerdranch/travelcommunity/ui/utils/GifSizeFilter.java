package com.bignerdranch.travelcommunity.ui.utils;

/**
 * @author zhongxinyu
 * @date 2020/5/4
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
import android.content.Context;
import android.graphics.Point;

import com.bignerdranch.travelcommunity.R;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.*;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.util.HashSet;
import java.util.Set;

class GifSizeFilter extends Filter {

    private int mMinWidth;
    private int mMinHeight;
    private int mMaxSize;

    GifSizeFilter(int minWidth, int minHeight, int maxSizeInBytes) {
        mMinWidth = minWidth;
        mMinHeight = minHeight;
        mMaxSize = maxSizeInBytes;
    }

    @Override
    public Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() {{
            add(MimeType.GIF);
        }};
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        Point size = PhotoMetadataUtils.getBitmapBound(context.getContentResolver(), item.getContentUri());
        if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
            return new IncapableCause(IncapableCause.DIALOG, context.getString(R.string.error_file_type, mMinWidth,
                    String.valueOf(PhotoMetadataUtils.getSizeInMB(mMaxSize))));
        }
        return null;
    }

}