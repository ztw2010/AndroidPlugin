package com.small.test.appstub.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhongruan on 2016/12/5.
 */

public class ValidateUtil {
    /**
     * 检验是否是手机号,默认返回true
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum)
    {
        if (!TextUtils.isEmpty(phoneNum))
        {
            String strPattern = "^[1][3,4,5,7,8][0-9]{9}$";
            Pattern p = Pattern.compile(strPattern);
            Matcher m = p.matcher(phoneNum);
            return m.matches();
        }
        return true;
    }

    /**
     * 密码正则
     * @param s
     * @return
     */
    public static boolean isPassword(String s)
    {
        String strPattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 校验是否是车牌号
     * @param carNum
     * @return
     */
    public static boolean isCarNum(String carNum)
    {
        if (!TextUtils.isEmpty(carNum))
        {
            Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");
            Matcher matcher = pattern.matcher(carNum);
            if (!matcher.matches())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return true;
    }

    /**
     * 姓名只能是1-8个中文字符
     * @param name
     * @return
     */
    public static boolean isCustomName(String name)
    {
        if (!TextUtils.isEmpty(name))
        {
            Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]{1,8}");
            Matcher matcher = pattern.matcher(name);
            if (!matcher.matches())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return true;
    }
}
