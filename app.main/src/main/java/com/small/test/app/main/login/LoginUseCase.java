package com.small.test.app.main.login;


import com.small.test.app.main.login.data.source.LoginDataSource;
import com.small.test.app.main.login.data.source.LoginRepository;
import com.small.test.app.main.login.data.source.LoginVO;
import com.small.test.appstub.mvp.UseCase;

/**
 * 
 * 登录关联类
 * <p>detailed comment
 * @author ztw 2016年7月1日
 * @see
 * @since 1.0
 */
public class LoginUseCase extends UseCase<LoginUseCase.RequestValues, LoginUseCase.ResponseValue>
{
    private final LoginRepository loginRepository;
    
    public LoginUseCase(LoginRepository loginRepository)
    {
        this.loginRepository = loginRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final String userName, userPsw;
        
        private final boolean isRememberPsw;
        
        public RequestValues(String userName, String userPsw, boolean isRememberPsw)
        {
            this.userName = userName;
            this.userPsw = userPsw;
            this.isRememberPsw = isRememberPsw;
        }
        
        public String getUserName()
        {
            return userName;
        }
        
        public String getUserPsw()
        {
            return userPsw;
        }
        
        public boolean isRememberPsw()
        {
            return isRememberPsw;
        }
    }
    
    public static final class ResponseValue implements UseCase.ResponseValue
    {
        private LoginVO loginDO;
        
        public ResponseValue(LoginVO loginDO)
        {
            this.loginDO = loginDO;
        }
        
        public LoginVO getLoginDO()
        {
            return loginDO;
        }
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues)
    {
        loginRepository.getLoginUser(requestValues.getUserName(),
            requestValues.getUserPsw(),
            requestValues.isRememberPsw(),
            new LoginDataSource.GetLoginUserCallBack()
            {
                
                @Override
                public void onUserLoaded(LoginVO loginDO)
                {
                    ResponseValue responseValue = new ResponseValue(loginDO);
                    getUseCaseCallback().onSuccess(responseValue);
                }
                
                @Override
                public void onError(Exception exception)
                {
                    getUseCaseCallback().onError(exception);
                }
            });
    }
}
