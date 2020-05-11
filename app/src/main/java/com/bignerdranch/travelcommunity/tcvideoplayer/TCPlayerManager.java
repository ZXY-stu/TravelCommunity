package com.bignerdranch.travelcommunity.tcvideoplayer;

import android.content.Context;

/**
 *
 * 类描述：获取唯一的视频控制器
 *
 */
public class TCPlayerManager {
    public static TCPlayerManager videoPlayViewManage;
    private TCPlayer videoPlayView;

    private TCPlayerManager() {

    }

    public static TCPlayerManager getMDManager() {
        if (videoPlayViewManage == null) {
            videoPlayViewManage = new TCPlayerManager();
        }
        return videoPlayViewManage;
    }

    public TCPlayer initialize(Context context) {
        if (videoPlayView == null) {
            videoPlayView = new TCPlayer(context);
        }
        return videoPlayView;
    }
}
