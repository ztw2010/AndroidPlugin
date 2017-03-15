package com.small.test.appstub.systeminit;

import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.small.test.appstub.log.L;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.network.OkHttpUtils;
import com.small.test.appstub.utils.device.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by ztw on 2016/12/20.
 */

public class SystemInit {

    private static SystemInit mInstance;

    /**
     * app存放文件的根目录
     */
    private File appRootFile;

    /**
     * 图片文件夹
     */
    private File upLoadImgFile;

    /**
     * 视频文件夹
     */
    private File upLoadVideoFile;

    /**
     * 下载文件夹
     */
    private File downLoadFile;

    /**
     * 数据库文件夹
     */
    private File dbRoot;

    private SystemInit() {

    }

    public static SystemInit getInstance() {
        if (mInstance == null) {
            synchronized (SystemInit.class) {
                if (mInstance == null) {
                    mInstance = new SystemInit();
                }
            }
        }
        return mInstance;
    }

    /**
     * 完成初始化工作
     * @return
     */
    public synchronized void init(Context context){
        if(appRootFile == null){
            L.d("init SystemInit class");
            Logger.init();
            OkHttpUtils.getInstance().setTimeout(C.API.C_DEFAULT_CONN_TIMEOUT,
                    TimeUnit.SECONDS,
                    C.API.C_DEFAULT_CONN_TIMEOUT,
                    TimeUnit.SECONDS,
                    C.API.C_DEFAULT_CONN_TIMEOUT,
                    TimeUnit.SECONDS);
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state))
            {
                appRootFile = new File(Environment.getExternalStorageDirectory(), C.MaintenanceRegistration.C_APP_ROOT_DIR);
                if(FileUtils.createOrExistsDir(appRootFile)){
                    L.d("appRootFileDir create success or has exists");
                }else{
                    L.e("appRootFileDir create failed,path="+appRootFile.getAbsolutePath());
                }
            }
            else
            {
                appRootFile = context.getFilesDir();
                if(FileUtils.createOrExistsDir(appRootFile)){
                    L.d("appRootFileDir create success or has exists");
                }else{
                    L.e("appRootFileDir create failed,path="+appRootFile.getAbsolutePath());
                }
            }
            upLoadImgFile = new File(appRootFile, C.MaintenanceRegistration.C_UPLOAD_IMG_DIR);
            if(FileUtils.createOrExistsDir(upLoadImgFile)){
                L.d("upLoadImgFile create success or has exists");
            }else{
                L.e("upLoadImgFile create failed,path="+upLoadImgFile.getAbsolutePath());
            }
            upLoadVideoFile = new File(appRootFile, C.MaintenanceRegistration.C_UPLOAD_VIDEO_DIR);
            if(FileUtils.createOrExistsDir(upLoadVideoFile)){
                L.d("upLoadVideoFile create success or has exists");
            }else{
                L.e("upLoadVideoFile create failed,path="+upLoadVideoFile.getAbsolutePath());
            }
            dbRoot = new File(appRootFile, C.MaintenanceRegistration.C_DB_DIR);
            if(FileUtils.createOrExistsDir(dbRoot)){
                L.d("dbRoot create success or has exists");
            }else{
                L.e("dbRoot create failed,path="+dbRoot.getAbsolutePath());
            }
            downLoadFile = new File(appRootFile, C.MaintenanceRegistration.C_DOWN_LOAD_DIR);
            if(FileUtils.createOrExistsDir(downLoadFile)){
                L.d("downLoadFile create success or has exists");
            }else{
                L.e("downLoadFile create failed,path="+downLoadFile.getAbsolutePath());
            }
        }
    }

    public File getAppRootFile() {
        return appRootFile;
    }

    public File getDbRoot() {
        return dbRoot;
    }

    public File getDownLoadFile() {
        return downLoadFile;
    }

    public static SystemInit getmInstance() {
        return mInstance;
    }

    public File getUpLoadImgFile() {
        return upLoadImgFile;
    }

    public File getUpLoadVideoFile() {
        return upLoadVideoFile;
    }
}
