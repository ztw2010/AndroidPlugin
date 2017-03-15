package com.small.test.appstub.loadimg;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.small.test.appstub.R;

import java.io.File;

/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlideImageLoader implements ImageLoader {

    private static GlideImageLoader mInstance;

    private  GlideImageLoader(){

    }

    public static GlideImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (GlideImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new GlideImageLoader();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void displayImage(Context context, String path, ImageView imageView, int width, int height) {
        Glide.with(context)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .crossFade()
                .error(R.mipmap.failed)                     //设置错误图片
                .placeholder(R.mipmap.ic_default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
