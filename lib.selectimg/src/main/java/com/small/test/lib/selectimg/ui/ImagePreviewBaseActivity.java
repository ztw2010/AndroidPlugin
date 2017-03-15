package com.small.test.lib.selectimg.ui;

import android.os.Bundle;
import android.view.View;

import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.R;
import com.small.test.lib.selectimg.adapter.ImagePageAdapter;
import com.small.test.lib.selectimg.bean.ImageItem;
import com.small.test.lib.selectimg.view.ViewPagerFixed;

import java.util.ArrayList;

/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：图片预览的基类
 * ================================================
 */
public abstract class ImagePreviewBaseActivity extends ImageBaseActivity {

    protected ImagePicker imagePicker;

    protected ArrayList<ImageItem> mImageItems;      //跳转进ImagePreviewFragment的图片文件夹

    protected int mCurrentPosition = 0;              //跳转进ImagePreviewFragment时的序号，第几个图片

    protected ArrayList<ImageItem> selectedImages;   //所有已经选中的图片

    protected View content;

    protected ViewPagerFixed mViewPager;

    protected ImagePageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        super.getLayoutId();
        return R.layout.activity_image_preview;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        if (titleBarView != null)
        {
            titleBarBackBtn.setVisibility(View.VISIBLE);
            backTxt.setVisibility(View.GONE);
            titleBarTitleTv.setVisibility(View.VISIBLE);
            titleBarTitleTv.setText(getString(R.string.preview));
            titleBarOtherTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mCurrentPosition = getIntent().getIntExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        mImageItems = (ArrayList<ImageItem>) getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        imagePicker = ImagePicker.getInstance();
        selectedImages = imagePicker.getSelectedImages();

        //初始化控件
        content = findViewById(R.id.content);

        mViewPager = (ViewPagerFixed) findViewById(R.id.viewpager);
        mAdapter = new ImagePageAdapter(this, mImageItems);
        mAdapter.setPhotoViewClickListener(new ImagePageAdapter.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1) {
                onImageSingleTap();
            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPosition, false);

        //初始化当前页面的状态
        titleBarOtherTv.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
    }

    /** 单击时，隐藏头和尾 */
    public abstract void onImageSingleTap();
}