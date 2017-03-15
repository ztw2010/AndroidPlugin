package com.small.test.app.main.login;


import com.small.test.appstub.mvp.IBasePresenter;
import com.small.test.appstub.mvp.IBaseView;

public interface LoginContract
{
    interface View extends IBaseView
    {
        /**
         * 记住密码
         */
        void rememberPsw(String userName, String userPsw);
        
        /**
         * 不记住密码
         */
        void unRemberPsw();
        
        void gotoMainpage();
    }
    
    interface Presenter extends IBasePresenter
    {
        public void doLogin(String userName, String userPsw, boolean isRememberPsw);
        
        public void geUserEntity();
    }
}
