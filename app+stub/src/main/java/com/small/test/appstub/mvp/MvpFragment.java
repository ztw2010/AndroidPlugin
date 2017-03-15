package com.small.test.appstub.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MvpFragment<P extends MvpPresenter> extends Fragment implements MvpView
{
    protected P mPresenter;

    protected String TAG = "";
    
    protected abstract View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflateView(inflater, container, savedInstanceState);
        initMvp();
        return view;
    };
    
    private void initMvp()
    {
        Mvp mvp = Mvp.getInstance();
        if (!TextUtils.isEmpty(TAG))
        {
            mvp.registerView(this.getClass(), TAG, this);
        }
        else
        {
            mvp.registerView(this.getClass(), this);
        }
        mPresenter = (P)mvp.getPresenter(Mvp.getGenericType(this, 0));
        mPresenter.initPresenter(getBaseView());
    }
    
    /**
     * 确定IView类型
     * @return
     */
    public abstract IBaseView getBaseView();
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (!TextUtils.isEmpty(TAG))
        {
            Mvp.getInstance().unRegister(this.getClass(), TAG);
        }
        else
        {
            Mvp.getInstance().unRegister(this.getClass());
        }
        mPresenter.destory();
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    public String getTAG()
    {
        return TAG;
    }
    
}
