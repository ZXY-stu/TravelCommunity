package com.bignerdranch.travelcommunity.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.drawerlayout.widget.DrawerLayout;

/**
 * @author zhongxinyu
 * @date 2020/5/12
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
public class UserDrawerLayout extends DrawerLayout {
    public UserDrawerLayout(Context context) {
        super(context);
    }

    public UserDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}