package com.small.test.lib.selectimg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.small.test.appstub.mvp.C;
import com.small.test.appstub.widget.dialog.AlertDialog;
import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.R;
import com.small.test.lib.selectimg.view.SuperCheckBox;


/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * ================================================
 */
public class ImagePreviewDelActivity extends ImagePreviewBaseActivity implements View.OnClickListener {

    private View bottomBar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setVisibility(View.VISIBLE);
        SuperCheckBox mCbCheck = (SuperCheckBox) findViewById(R.id.cb_check);
        mCbCheck.setVisibility(View.GONE);
        SuperCheckBox mCbOrigin = (SuperCheckBox) findViewById(R.id.cb_origin);
        mCbOrigin.setVisibility(View.GONE);
        ImageView mBtnDel = (ImageView) findViewById(R.id.btn_del);
        mBtnDel.setOnClickListener(this);
        mBtnDel.setVisibility(View.VISIBLE);

        titleBarOtherTv.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
        //滑动ViewPager的时候，根据外界的数据改变当前的选中状态和当前的图片的位置描述文本
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                titleBarOtherTv.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.btn_del) {
            showDeleteDialog();
        }
    }

    /** 是否删除此张图片 */
    private void showDeleteDialog() {
        new AlertDialog(this).builder()
                .setCanceledOnTouchOutside(true)
                .setCancelable(true)
                .setTitle(getString(R.string.tips))
                .setMsg(getString(R.string.sure_to_delete))
                .setPositiveButton(getString(R.string.sure), new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //移除当前图片刷新界面
                        mImageItems.remove(mCurrentPosition);
                        if (mImageItems.size() > 0) {
                            mAdapter.setData(mImageItems);
                            mAdapter.notifyDataSetChanged();
                            titleBarOtherTv.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
                        } else {
                            goBack();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    /** 单击时，隐藏头和尾 */
    @Override
    public void onImageSingleTap() {
        if(titleBarView != null){
            if (titleBarView.getVisibility() == View.VISIBLE) {
                titleBarView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));
                titleBarView.setVisibility(View.GONE);
            } else {
                titleBarView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_in));
                titleBarView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void goBack() {
        super.goBack();
        Intent intent = new Intent();
        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, mImageItems);
        setResult(C.MaintenanceRegistration.C_RESULTCODE_OK, intent);
        finish();
    }
}