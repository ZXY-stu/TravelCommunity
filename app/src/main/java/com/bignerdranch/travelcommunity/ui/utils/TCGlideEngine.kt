package com.bignerdranch.travelcommunity.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bignerdranch.travelcommunity.util.GlideUtil
import com.bumptech.glide.Glide
import com.zhihu.matisse.engine.ImageEngine

/**
 * @author zhongxinyu
 * @date 2020/5/22
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
class TCGlideEngine:ImageEngine {

    override fun loadImage(
        context: Context?,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView?,
        uri: Uri?
    ) {
          Glide.with(context)
              .load(uri)
              .apply(GlideUtil.getImageOption(resizeX,resizeY))
              .into(imageView)
    }

    override fun loadGifImage(
        context: Context?,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView?,
        uri: Uri?
    ) {
        Glide.with(context)
            .asGif()
            .load(uri)
            .apply(GlideUtil.getImageOption(resizeX,resizeY))
            .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean {
                return true
    }

    override fun loadGifThumbnail(
        context: Context?,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView?,
        uri: Uri?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .apply(GlideUtil.getImageOption(resize,resize).placeholder(placeholder).centerCrop())
            .into(imageView);
    }

    override fun loadThumbnail(
        context: Context?,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView?,
        uri: Uri?
    ) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .apply(GlideUtil.getImageOption(resize,resize).placeholder(placeholder).centerCrop())
            .into(imageView);
    }
}