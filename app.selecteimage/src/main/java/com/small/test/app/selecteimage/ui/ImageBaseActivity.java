package com.small.test.app.selecteimage.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.small.test.app.selecteimage.R;
import com.small.test.app.selecteimage.view.SystemBarTintManager;
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

    protected SystemBarTintManager tintManager;

    /**
     * 读取SDCard requestCode
     */
    public static final int STORAGEREQUESTCODE = 0x01;

    /**
     * camera requestCode
     */
    public static final int CAMERAQUESTCODE = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.status_bar);  //设置上方状态栏的颜色
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public IBaseView getBaseView() {
        return this;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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
