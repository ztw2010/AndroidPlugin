package com.small.test.lib.selectimg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.small.test.appstub.mvp.C;
import com.small.test.appstub.widget.dialog.AlertDialog;
import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.R;
import com.small.test.lib.selectimg.bean.ImageItem;
import com.small.test.lib.selectimg.view.SuperCheckBox;


/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImagePreviewActivity extends ImagePreviewBaseActivity implements ImagePicker.OnImageSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String ISORIGIN = "isOrigin";

    private boolean isOrigin;                      //是否选中原图

    private SuperCheckBox mCbCheck;                //是否选中当前图片的CheckBox

    private SuperCheckBox mCbOrigin;               //原图

    private View bottomBar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        isOrigin = getIntent().getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
        imagePicker.addOnImageSelectedListener(this);
        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setVisibility(View.VISIBLE);
        mCbCheck = (SuperCheckBox) findViewById(R.id.cb_check);
        mCbOrigin = (SuperCheckBox) findViewById(R.id.cb_origin);
        mCbOrigin.setText(getString(R.string.origin));
        mCbOrigin.setOnCheckedChangeListener(this);
        mCbOrigin.setChecked(isOrigin);
        ImageView mBtnDel = (ImageView) findViewById(R.id.btn_del);
        mBtnDel.setVisibility(View.GONE);
        //初始化当前页面的状态
        onImageSelected(0, null, false);
        ImageItem item = mImageItems.get(mCurrentPosition);
        boolean isSelected = imagePicker.isSelect(item);
        titleBarOtherTv.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
        mCbCheck.setChecked(isSelected);
        //滑动ViewPager的时候，根据外界的数据改变当前的选中状态和当前的图片的位置描述文本
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                ImageItem item = mImageItems.get(mCurrentPosition);
                boolean isSelected = imagePicker.isSelect(item);
                mCbCheck.setChecked(isSelected);
                titleBarOtherTv.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
            }
        });
        //当点击当前选中按钮的时候，需要根据当前的选中状态添加和移除图片
        mCbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageItem imageItem = mImageItems.get(mCurrentPosition);
                int selectLimit = imagePicker.getSelectLimit() - imagePicker.getSelectedVideo();
                if (mCbCheck.isChecked() && selectedImages.size() >= selectLimit) {
                    new AlertDialog(ImagePreviewActivity.this).builder()
                            .setCanceledOnTouchOutside(false)
                            .setTitle(getString(R.string.tips))
                            .setMsg(getString(R.string.select_limit, selectLimit))
                            .setPositiveButton(getString(R.string.sure), null)
                            .show();
                    mCbCheck.setChecked(false);
                } else {
                    imagePicker.addSelectedImageItem(mCurrentPosition, imageItem, mCbCheck.isChecked());
                }
            }
        });
    }

    /**
     * 图片添加成功后，修改当前图片的选中数量
     * 当调用 addSelectedImageItem 或 deleteSelectedImageItem 都会触发当前回调
     */
    @Override
    public void onImageSelected(int position, ImageItem item, boolean isAdd) {
        if (imagePicker.getSelectImageCount() > 0) {
            titleBarOtherTv.setText(getString(R.string.select_complete, imagePicker.getSelectImageCount(), imagePicker.getSelectLimit() - imagePicker.getSelectedVideo()));
        } else {
            titleBarOtherTv.setText(getString(R.string.complete));
        }

        if (mCbOrigin.isChecked()) {
            long size = 0;
            for (ImageItem imageItem : selectedImages)
                size += imageItem.size;
            String fileSize = Formatter.formatFileSize(this, size);
            mCbOrigin.setText(getString(R.string.origin_size, fileSize));
        }
    }

    @Override
    protected void goBack() {
        super.goBack();
        Intent intent = new Intent();
        intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
        setResult(ImagePicker.RESULT_CODE_BACK, intent);
        finish();
    }

    @Override
    protected void processOther() {
        super.processOther();
        Intent intent = new Intent();
        intent.putExtra(C.MaintenanceRegistration.KEY_SELECTED_PIC, imagePicker.getSelectedImages());
        setResult(ImagePicker.RESULT_CODE_ITEMS, intent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.cb_origin) {
            if (isChecked) {
                long size = 0;
                for (ImageItem item : selectedImages)
                    size += item.size;
                String fileSize = Formatter.formatFileSize(this, size);
                isOrigin = true;
                mCbOrigin.setText(getString(R.string.origin_size, fileSize));
            } else {
                isOrigin = false;
                mCbOrigin.setText(getString(R.string.origin));
            }
        }
    }

    @Override
    protected void onDestroy() {
        imagePicker.removeOnImageSelectedListener(this);
        super.onDestroy();
    }

    /** 单击时，隐藏头和尾 */
    @Override
    public void onImageSingleTap() {
        if(titleBarView != null){
            if (titleBarView.getVisibility() == View.VISIBLE) {
                titleBarView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));
                bottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                titleBarView.setVisibility(View.GONE);
                bottomBar.setVisibility(View.GONE);
            } else {
                titleBarView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_in));
                bottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                titleBarView.setVisibility(View.VISIBLE);
                bottomBar.setVisibility(View.VISIBLE);
            }
        }
    }
}
