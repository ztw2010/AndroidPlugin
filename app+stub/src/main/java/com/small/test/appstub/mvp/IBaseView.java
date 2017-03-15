package com.small.test.appstub.mvp;

public interface IBaseView
{
    /**
     * 显示加载数据的进度条
     */
    void showLoading();

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 显示提示框
     * @param content 提示内容
     */
    void showTip(String content);
}
