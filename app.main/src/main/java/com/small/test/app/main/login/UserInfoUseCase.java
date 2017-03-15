package com.small.test.app.main.login;


import com.small.test.app.main.login.data.UserVO;
import com.small.test.app.main.login.data.source.LoginDataSource;
import com.small.test.app.main.login.data.source.LoginRepository;
import com.small.test.appstub.mvp.UseCase;

public class UserInfoUseCase extends UseCase<UserInfoUseCase.RequestValues, UserInfoUseCase.ResponseValue>
{
    private final LoginRepository loginRepository;
    
    public UserInfoUseCase(LoginRepository loginRepository)
    {
        this.loginRepository = loginRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
    }
    
    public static final class ResponseValue implements UseCase.ResponseValue
    {
        private UserVO userEntity;
        
        public ResponseValue(UserVO userEntity)
        {
            this.userEntity = userEntity;
        }
        
        public UserVO getUserEntity()
        {
            return userEntity;
        }
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues)
    {
        loginRepository.getUserInfo(new LoginDataSource.UserInfo()
        {
            
            @Override
            public void userInfo(UserVO userEntity)
            {
                ResponseValue responseValue = new ResponseValue(userEntity);
                getUseCaseCallback().onSuccess(responseValue);
            }
        });
    }
}
