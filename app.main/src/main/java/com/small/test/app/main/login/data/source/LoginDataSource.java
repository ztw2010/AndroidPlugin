package com.small.test.app.main.login.data.source;


import com.small.test.app.main.login.data.UserVO;

public interface LoginDataSource
{
    interface GetLoginUserCallBack
    {
        void onUserLoaded(LoginVO loginDO);
        
        void onError(Exception exception);
    }
    
    interface UserInfo
    {
        void userInfo(UserVO userEntity);
    }
    
    public void getLoginUser(String userName, String userPsw, boolean isRememberPsw,
        LoginDataSource.GetLoginUserCallBack getLoginUserCallBack);
    
    public void getUserInfo(LoginDataSource.UserInfo callBack);
    
    /**
     * 清理其他用户的本地记录
     */
    public void clearTableByOpUser();
    
    /**
     * 将该用户上次未上传完成的图片和视频等附件的状态置为上传失败
     */
    public void processUploadingTask();
}
