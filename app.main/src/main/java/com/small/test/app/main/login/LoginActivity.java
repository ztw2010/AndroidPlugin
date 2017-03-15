package com.small.test.app.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.small.test.app.main.MainActivity;
import com.small.test.app.main.R;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.ManagerActivity;
import com.small.test.appstub.mvp.MvpActivity;
import com.small.test.appstub.widget.clearedittext.ClearEditText;


public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginContract.View
{
    public ClearEditText et1;

    public ClearEditText et2;

    public Button mLogin;

    public TextView mForgPa;

    public Button mLogin2;

    public CheckBox checkBox1;

    private Long lastExitTime = 0l;

    @Override
    protected int getLayoutId(){
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState){
        et1 = (ClearEditText)findViewById(R.id.jobId);
        et2 = (ClearEditText)findViewById(R.id.password);
        mLogin = (Button)findViewById(R.id.bt_login);
        mLogin.setOnClickListener(this);
        mForgPa = (TextView)findViewById(R.id.tv_fo);
        mForgPa.setOnClickListener(this);
        mLogin2 = (Button)findViewById(R.id.bt_login2);
        checkBox1 = (CheckBox)findViewById(R.id.cb);

        et1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                setViews();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                setViews();
            }
        });
        et2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                setViews();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                setViews();
            }
        });
        setViews();
        mPresenter.geUserEntity();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.bt_login:
                mPresenter.doLogin(et1.getText().toString(), et2.getText().toString(), checkBox1.isChecked());
                break;
            case R.id.tv_fo:
                //Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                //startActivity(intent);
                //finish();
                break;
            default:
                break;
        }
    }

    @Override
    public IBaseView getBaseView()
    {
        return this;
    }

    private void setViews()
    {
        if (TextUtils.isEmpty(et1.getText().toString()) && TextUtils.isEmpty(et2.getText().toString()))
        {
            mLogin.setVisibility(View.GONE);
            mLogin2.setVisibility(View.VISIBLE);
        }
        else if (!TextUtils.isEmpty(et1.getText().toString()) && TextUtils.isEmpty(et2.getText().toString()))
        {
            mLogin.setVisibility(View.GONE);
            mLogin2.setVisibility(View.VISIBLE);
        }
        else if (TextUtils.isEmpty(et1.getText().toString()) && !TextUtils.isEmpty(et2.getText().toString()))
        {
            mLogin.setVisibility(View.GONE);
            mLogin2.setVisibility(View.VISIBLE);
        }
        else if (!TextUtils.isEmpty(et1.getText().toString()) && !TextUtils.isEmpty(et2.getText().toString()))
        {
            mLogin.setVisibility(View.VISIBLE);
            mLogin2.setVisibility(View.GONE);
        }

    }

    @Override
    public void rememberPsw(String userName, String userPsw)
    {
        checkBox1.setChecked(true);
        et1.setText(userName);
        et2.setText(userPsw);
    }

    @Override
    public void unRemberPsw()
    {
        checkBox1.setChecked(false);
        et1.setText("");
        et2.setText("");
    }

    @Override
    public void gotoMainpage()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {
        svProgressHUD.showWithStatus(getString(R.string.loading_data));
    }

    @Override
    public void hideLoading() {
        svProgressHUD.dismissImmediately();
    }

    @Override
    public void showTip(String content) {
        svProgressHUD.showInfoWithStatus(content);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((System.currentTimeMillis() - lastExitTime) < 2000){
            ManagerActivity.getInstance().exit();
            Intent mIntent = new Intent(Intent.ACTION_MAIN);
            mIntent.addCategory(Intent.CATEGORY_HOME);
            startActivity(mIntent);
        }else{
            Toast.makeText(this, getString(R.string.double_click_exit), Toast.LENGTH_SHORT).show();
            lastExitTime = System.currentTimeMillis();
        }
        return false;
    }
}
