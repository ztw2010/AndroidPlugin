package com.small.test.appstub.loadimg;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：ImageLoader抽象类，外部需要实现这个类去加载图片， 尽力减少对第三方库的依赖，所以这么干了
 * 修订历史：
 * ================================================
 */
public interface ImageLoader extends Serializable {

    void displayImage(Context context, String path, ImageView imageView, int width, int height);

    void clearMemoryCache();
}
