package com.small.test.lib.selectimg.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.small.test.appstub.mvp.DefaultMvpContract;
import com.small.test.appstub.mvp.DefaultMvpPresenter;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.MvpActivity;


/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImageBaseActivity extends MvpActivity<DefaultMvpPresenter> implements DefaultMvpContract.View {

    /**
     * 读取SDCard requestCode
     */
    public static final int STORAGEREQUESTCODE = 0x01;

    /**
     * camera requestCode
     */
    public static final int CAMERAQUESTCODE = 0x02;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public IBaseView getBaseView() {
        return this;
    }

    public void showToast(String toastText) {
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showTip(String content) {
    }
}
