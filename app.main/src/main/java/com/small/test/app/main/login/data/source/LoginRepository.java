package com.small.test.app.main.login.data.source;

import android.text.TextUtils;

import com.small.test.app.main.login.data.UserVO;
import com.small.test.appstub.VO.ResultModel;
import com.small.test.appstub.cache.ACache;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.MvpModel;
import com.small.test.appstub.network.OkHttpUtils;
import com.small.test.appstub.network.callback.StringCallback;
import com.small.test.appstub.utils.AESUtils;
import com.small.test.appstub.utils.ShareUtils;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoginRepository extends MvpModel implements LoginDataSource
{
    @Override
    public void getLoginUser(final String userName, final String userPsw, final boolean isRememberPsw,
        final LoginDataSource.GetLoginUserCallBack getLoginUserCallBack)
    {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(C.Login.PARAMS_JOBID, userName);
        map.put(C.Login.PARAMS_PASSWORD, userPsw);
        try
        {
            OkHttpUtils.get(context)
                .url(String.format(C.API.C_API_ROOT, C.Login.API_METHOD_LOGIN))
                .params(map)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        ResultModel<LoginVO> resultModel = objectMapper.readValue(response,
                            objectMapper.getJavaType(ResultModel.class, LoginVO.class));
                        if (resultModel != null)
                        {
                            if (ResultModel.RESULTSUCCESS == resultModel.getServFlag())
                            {
                                if (isRememberPsw)
                                {
                                    ShareUtils.getInstance().updateValue(C.Login.KEY_JOBID, userName);
                                    ShareUtils.getInstance().updateValue(C.Login.KEY_PASSWORD,
                                        AESUtils.encrypt(C.Login.C_PASSWORDKEY, userPsw));
                                    ShareUtils.getInstance().updateValue(C.Login.C_ISMEMORY, C.Login.VALUE_YES);
                                }
                                else
                                {
                                    ShareUtils.getInstance().clearValue(C.Login.C_ISMEMORY, C.Login.KEY_PASSWORD, C.Login.KEY_JOBID);
                                }
                                LoginVO loginVO = resultModel.getData().iterator().next();
                                getLoginUserCallBack.onUserLoaded(loginVO);
                                ACache aCache = ACache.get(context);
                                aCache.put(C.API.KEY_CACHE_LOGIN_VO, loginVO);
                            }
                            else if (ResultModel.RESULTFAILED == resultModel.getServFlag())
                            {
                                String errorMsg = resultModel.getErrorMsg();
                                if (!TextUtils.isEmpty(errorMsg))
                                {
                                    getLoginUserCallBack.onError(new Exception(errorMsg));
                                }
                            }
                        }
                        
                    }
                    
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        getLoginUserCallBack.onError(e);
                    }
                });
        }
        catch (ConnectException e)
        {
            getLoginUserCallBack.onError(e);
        }
    }
    
    @Override
    public void getUserInfo(LoginDataSource.UserInfo userInfoCallBack)
    {
        UserVO userVO = new UserVO();
        userVO.setIsMemory(ShareUtils.getInstance().getValue(C.Login.C_ISMEMORY, C.Login.VALUE_NO));
        userVO.setJobId(ShareUtils.getInstance().getValue(C.Login.KEY_JOBID, ""));
        userVO.setPassword(AESUtils.decrypt(C.Login.C_PASSWORDKEY, ShareUtils.getInstance().getValue(C.Login.KEY_PASSWORD, "")));
        userInfoCallBack.userInfo(userVO);
    }
    
    @Override
    public void clearTableByOpUser()
    {
    }
    
    @Override
    public void processUploadingTask()
    {
    }
}
