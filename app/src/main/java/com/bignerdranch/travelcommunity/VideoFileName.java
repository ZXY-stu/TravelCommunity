package com.bignerdranch.travelcommunity;

import android.net.Uri;

import com.bignerdranch.travelcommunity.videocache.file.FileNameGenerator;

/**
 * @author zhongxinyu
 * @date 2020/4/29
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
public class VideoFileName implements FileNameGenerator {

    // Urls contain mutable parts (parameter 'sessionToken') and stable video's id (parameter 'videoId').
    // e. g. http://example.com?videoId=abcqaz&sessionToken=xyz987
    public String generate(String url) {
        Uri uri = Uri.parse(url);
        String videoId = uri.getQueryParameter("videoId");
        return videoId + ".mp4";
    }
}
