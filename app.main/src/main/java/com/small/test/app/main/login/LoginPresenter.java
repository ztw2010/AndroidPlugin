package com.small.test.app.main.login;

import android.text.TextUtils;

import com.small.test.app.main.R;
import com.small.test.app.main.login.data.UserVO;
import com.small.test.app.main.login.data.source.LoginRepository;
import com.small.test.app.main.login.data.source.LoginVO;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.ErrorMessageFactory;
import com.small.test.appstub.mvp.MvpPresenter;
import com.small.test.appstub.mvp.UseCase;
import com.small.test.appstub.utils.ValidateUtil;

public class LoginPresenter extends MvpPresenter<LoginRepository, LoginContract.View> implements LoginContract.Presenter
{
    private LoginUseCase loginUseCase;
    
    private UserInfoUseCase userInfoUseCase;
    
    @Override
    protected void initUsecase()
    {
        loginUseCase = new LoginUseCase(mModel);
        userInfoUseCase = new UserInfoUseCase(mModel);
    }
    
    @Override
    public void doLogin(final String userName, String userPsw, boolean isRememberPsw)
    {
        if (!TextUtils.isEmpty(userName) && (!TextUtils.isEmpty(userPsw)) && (!ValidateUtil.isPassword(userPsw)))
        {
            iView.showTip(mContext.getString(R.string.hint_password));
        }
        else
        {
            iView.showLoading();
            mUseCaseHandler.execute(loginUseCase,
                new LoginUseCase.RequestValues(userName, userPsw, isRememberPsw),
                new UseCase.UseCaseCallback<LoginUseCase.ResponseValue>()
                {
                    
                    @Override
                    public void onSuccess(LoginUseCase.ResponseValue response)
                    {
                        iView.hideLoading();
                        LoginVO loginVO = response.getLoginDO();
                        loginVO.setJobId(userName);
                        iView.gotoMainpage();
                    }
                    
                    @Override
                    public void onError(Exception exception)
                    {
                        iView.hideLoading();
                        iView.showTip(ErrorMessageFactory.create(mContext, exception));
                    }
                });
        }
    }
    
    @Override
    public void geUserEntity()
    {
        mUseCaseHandler.execute(userInfoUseCase,
            new UserInfoUseCase.RequestValues(),
            new UseCase.UseCaseCallback<UserInfoUseCase.ResponseValue>()
            {
                
                @Override
                public void onSuccess(UserInfoUseCase.ResponseValue response)
                {
                    UserVO userVO = response.getUserEntity();
                    if (userVO != null)
                    {
                        String isMemory = userVO.getIsMemory();
                        if (C.Login.KEY_YES.equals(isMemory))
                        {
                            String userName = userVO.getJobId();
                            String userPsw = userVO.getPassword();
                            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPsw))
                            {
                                iView.rememberPsw(userName, userPsw);
                            }
                            else
                            {
                                iView.unRemberPsw();
                            }
                        }
                        else if (C.Login.KEY_NO.equals(isMemory))
                        {
                            iView.unRemberPsw();
                        }
                    }
                }
                
                @Override
                public void onError(Exception exception)
                {
                    iView.hideLoading();
                }
            });
    }
    
}
