package com.small.test.lib.selectimg.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：图片信息
 * 修订历史：
 * ================================================
 */
public class ImageItem implements Serializable {

    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public int width;         //图片的宽度
    public int height;        //图片的高度
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间
    public boolean isCamera = false;

    public ImageItem(){
        isCamera = true;
    }

    public ImageItem(long addTime, int height, String mimeType, String name, String path, long size, int width) {
        this.addTime = addTime;
        this.height = height;
        this.mimeType = mimeType;
        this.name = name;
        this.path = path;
        this.size = size;
        this.width = width;
    }

    /** 图片的路径和创建时间相同就认为是同一张图片 */
    @Override
    public boolean equals(Object o) {
        try {
            ImageItem other = (ImageItem) o;
            if (!TextUtils.isEmpty(this.path) && !TextUtils.isEmpty(other.path)){
                return this.path.equalsIgnoreCase(other.path);
            }else if (other.isCamera){
                return true;
            }else{
                return false;
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }

    public ImageItem copyNew()
    {
        return new ImageItem(this.addTime, this.height, this.mimeType, this.name, this.path, this.size, this.width);
    }
}
