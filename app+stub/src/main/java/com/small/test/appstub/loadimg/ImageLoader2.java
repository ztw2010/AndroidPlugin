package com.small.test.appstub.loadimg;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by zhongruan on 2016/12/16.
 */

public interface ImageLoader2 extends Serializable {
    public void displayImage(Context context, String path, ImageView imageView);
}
