package com.small.test.appstub.mvp;

import android.content.Context;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class MvpPresenter<M extends MvpModel, V extends IBaseView>
{
    protected Context mContext;
    
    protected Reference<V> mViewRef;
    
    protected UseCaseHandler mUseCaseHandler;
    
    protected V iView;
    
    protected M mModel;
    
    public void initPresenter(V view)
    {
        mModel = (M)Mvp.getInstance().getModel(Mvp.getGenericType(this, 0));
        mViewRef = new WeakReference<V>(view);
        iView = view;
        mContext = Mvp.getInstance().getApplictionContext();
        mUseCaseHandler = UseCaseHandler.getInstance();
        initUsecase();
    }
    
    protected abstract void initUsecase();

    public M getModel()
    {
        return mModel;
    }
    
    /**
     * 在此处清理各个presenter的临时变量(因为Mvp.registerPresenter缓存了presenter变量，因此需要清理)，各种useCase变量不可清理
     */
    public void destory()
    {
        if (mViewRef != null)
        {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
