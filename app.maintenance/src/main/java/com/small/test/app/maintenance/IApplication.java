package com.small.test.app.maintenance;

import android.app.Application;

import com.small.test.appstub.loadimg.GlideImageLoader;
import com.small.test.appstub.systeminit.SystemInit;
import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.view.CropImageView;

/**
 * Created by ztw on 2016/12/20.
 * 完成模块的初始化工作
 */

public class IApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SystemInit.getInstance().init(this.getApplicationContext());
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(GlideImageLoader.getInstance());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(4);                          //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
        imagePicker.setSavePicPath(SystemInit.getInstance().getUpLoadImgFile().getAbsolutePath());
    }
}
