package com.small.test.appstub.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.test.appstub.R;
import com.small.test.appstub.network.OkHttpUtils;
import com.small.test.appstub.widget.svgprogresshud.SVProgressHUD;


/**
 *
 * Activity基础类
 * <p>detailed comment
 * @author ztw 2016年8月9日
 * @see
 * @since 1.0
 * @param <P>
 */
public abstract class MvpActivity<P extends MvpPresenter> extends FragmentActivity implements MvpView, View.OnClickListener
{
    protected P mPresenter;

    protected SVProgressHUD svProgressHUD;

    protected LinearLayout backContainer;

    protected Button titleBarBackBtn;

    protected Button searchBtn;

    protected TextView titleBarTitleTv;

    protected TextView titleBarOtherTv;

    protected TextView backTxt;

    protected View titleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ManagerActivity.getInstance().addActivity(this);
        svProgressHUD = new SVProgressHUD(this);
        initTitleView();
        initMvp();
        initView(savedInstanceState);
    }

    private void initTitleView(){
        titleBarView = findViewById(R.id.titleBarView);
        if(titleBarView != null){
            backContainer = (LinearLayout)titleBarView.findViewById(R.id.back_container);
            backContainer.setOnClickListener(this);
            titleBarBackBtn = (Button)titleBarView.findViewById(R.id.back_btn);
            searchBtn = (Button)titleBarView.findViewById(R.id.search_btn);
            searchBtn.setVisibility(View.GONE);
            titleBarBackBtn.setOnClickListener(this);
            titleBarTitleTv = (TextView)titleBarView.findViewById(R.id.page_title_txt);
            titleBarOtherTv = (TextView)titleBarView.findViewById(R.id.other_txt);
            titleBarOtherTv.setOnClickListener(this);
            backTxt = (TextView)titleBarView.findViewById(R.id.back_txt);
        }
        initTitle();
    }

    protected abstract  int getLayoutId();

    protected abstract  void initView(Bundle savedInstanceState);

    protected abstract void initTitle();

    public void initMvp()
    {
        Mvp mvp = Mvp.getInstance();
        mvp.init(this.getApplicationContext());
        mvp.registerView(this.getClass(), this);
        mPresenter = (P)mvp.getPresenter(Mvp.getGenericType(this, 0));
        mPresenter.initPresenter(getBaseView());
    }

    /**
     * 确定IView类型
     * @return
     */
    public abstract IBaseView getBaseView();

    @Override
    protected void onDestroy()
    {
        if(svProgressHUD.isShowing()){
            svProgressHUD.dismissImmediately();
        }
        destroyHttpRequest();
        Mvp.getInstance().unRegister(this.getClass());
        mPresenter.destory();
        super.onDestroy();
        ManagerActivity.getInstance().removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        int resId = v.getId();
        if(R.id.back_container == resId || R.id.back_btn == resId){
            goBack();
        }else if(R.id.other_txt == resId){
            processOther();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goBack();
        }
        return false;
    }

    protected void goBack(){

    }

    protected void processOther(){

    }

    /**
     * 页面销毁时取消该页面的所有的http请求
     */
    private void destroyHttpRequest(){
        OkHttpUtils.getInstance().cancelTag(mPresenter.getModel().HTTP_TASK_KEY);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev))
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev))
        {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 实现点击EditText之外的任意一个位置后隐藏系统软件盘
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event)
    {
        if (v != null && (v instanceof EditText))
        {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return false;
    }
}
