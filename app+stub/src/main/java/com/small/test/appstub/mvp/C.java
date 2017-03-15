package com.small.test.appstub.mvp;

/**
 * 
 * 系统常量
 * 以PARAMS_  开头代表http请求中参数的key
 * 以KEY_ 开头代表常量key
 * 以VALUE_ 开头代表常量key的
 * 以C_INDEX_ 开头代表索引常量
 * 以C_ 开头代表基本常量
 * API_METHOD_ 开头代表接口名字
 * <p>detailed comment
 * @author ztw 2016年6月30日
 * @see
 * @since 1.0
 */
public final class C
{
    /**
     * 
     * 登录模块常量
     * <p>detailed comment
     * @author ztw 2016年6月30日
     * @see
     * @since 1.0
     */
    public static final class Login
    {
        /**
         * http参数工号字段key
         */
        public static final String PARAMS_JOBID = "jobId";
        
        /**
         * http参数密码字段key
         */
        public static final String PARAMS_PASSWORD = "password";
        
        /**
         * 是否记住密码
         */
        public static final String C_ISMEMORY = "isMemory";
        
        /**
         * SharedPreference记住密码key
         */
        public static final String KEY_YES = "yes";
        
        /**
         * SharedPreference记住密码value
         */
        public static final String VALUE_YES = "yes";
        
        /**
         * SharedPreference不记住密码key
         */
        public static final String KEY_NO = "no";
        
        /**
         * SharedPreference不记住密码value
         */
        public static final String VALUE_NO = "no";
        
        /**
         * SharedPreference工号key
         */
        public static final String KEY_JOBID = "jobId";
        
        /**
         * SharedPreference密码key
         */
        public static final String KEY_PASSWORD = "password";
        
        /**
         * 登录方法名
         */
        public static final String API_METHOD_LOGIN = "login";
        
        /**
         * 密码种子
         */
        public static final String C_PASSWORDKEY = "41227677";
        
        /**
         * 城市Id
         */
        public final static String PARAMS_CITYID = "cityid";
        
        /**
         * 天气的key
         */
        public final static String PARAMS_KEY = "key";
        
        /**
         * 天气key的value
         */
        public final static String VALUE_KEYVALUE = "2be30ddf2c6047a6940beaffcf1eb7a5";
    }
    
    /**
     * 
     * 忘记密码常量
     *
     * <p>detailed comment
     * @author ztw 2016年7月4日
     * @see
     * @since 1.0
     */
    public static final class ForgetPassword
    {
        /**
         * http参数手机号字段key
         */
        public static final String PARAMS_PHONENUMBER = "phoneNumber";
        
        /**
         * 获取验证码方法名
         */
        public static final String API_METHOD_VERICODE = "veriCode";
        
        /**
         * 获取验证码超时时间60s
         */
        public static final int C_VERICODE_TOTAL_TIME = 60 * 1000;
        
        /**
         * 每隔1s进行倒计时
         */
        public static final int C_VERICODE_INTERVAL_TIME = 1 * 1000;
    }
    
    /**
     * 
     * 忘记密码页常量
     *
     * <p>detailed comment
     * @author ztw 2016年7月5日
     * @see
     * @since 1.0
     */
    public static final class ConfirmPassword
    {
        /**
         * http参数新密码字段key
         */
        public final static String PARAMS_NEWPASSWORD = "newPassword";
        
        /**
         * http参数旧密码字段key
         */
        public final static String PARAMS_OLDPASSWORD = "oldPassword";
        
        /**
         * 忘记密码接口
         */
        public static final String API_METHOD_PASSWORD = "password";
    }
    
    /**
     * 
     * 历史数据页常量
     *
     * <p>detailed comment
     * @author ztw 2016年7月5日
     * @see
     * @since 1.0
     */
    public static final class HistoryData
    {
        /**
         * http参数父级ID字段key
         */
        public final static String PARAMS_PARENTCODE = "parentCode";
        
        /**
         * http参数页码字段key
         */
        public final static String PARAMS_PAGENO = "pageNo";
        
        /**
         * http参数每页显示几条字段key
         */
        public final static String PARAMS_RECORDNUM = "recordNum";
        
        /**
         * http参数类别字段key
         */
        public final static String PARAMS_CLASSCODE = "classCode";
        
        /**
         * 获取一级二级总成数据接口
         */
        public static final String API_METHOD_CATEGORYLIST = "categoryList";
        
        /**
         * 获取安装位置数据接口
         */
        public static final String API_METHOD_POSITIONLIST = "positionList";
        
        /**
         * 索引:安装位置
         */
        public static final int C_INDEX_SYSTYPE = 1;
        
        /**
         * 索引:一级系统
         */
        public static final int C_INDEX_SYSFIRST = 2;
        
        /**
         * 索引:二级系统
         */
        public static final int C_INDEX_SYSSECOND = 3;
        
        /**
         * 索引:总成
         */
        public static final int C_INDEX_ZONGCHENG = 4;
        
        /**
         * 索引:总成上下拉
         */
        public static final int C_INDEX_ZONGCHENG_REFRESH = 5;
        
        /**
         * 安装位置的classcode
         */
        public static final int VALUE_CLASSCODE_0 = 0;
        
        /**
         * 一级系统的classcode
         */
        public static final int VALUE_CLASSCODE_1 = 1;
        
        /**
         * 二级系统的classcode
         */
        public static final int VALUE_CLASSCODE_2 = 2;
        
        /**
         * 总成的classcode
         */
        public static final int VALUE_CLASSCODE_3 = 3;
        
        /**
         * Bundle传值key  代表总成Code
         */
        public static final String KEY_ZONGCHENG_CODE = "zongchengCode";
    }
    
    /**
     * 
     * 故障列表页常量
     *
     * <p>detailed comment
     * @author ztw 2016年7月12日
     * @see
     * @since 1.0
     */
    public static final class Suspension
    {
        /**
         * http参数:className字段key
         */
        public static final String PARAMS_CLASSNAME = "className";
        
        /**
         * http参数:assemblyId字段key
         */
        public static final String PARAMS_ASSEMBLYID = "assemblyId";
        
        /**
         * http参数:PARAMS_CLASSNAME字段对应的value
         */
        public static final String VALUE_SERVICE_MANUAL = "SERVICE_MANUAL";
        
        /**
         * 获取故障列表接口
         */
        public static final String API_METHOD_PHENBYCATEGORY = "phenByCategory";
        
        /**
         * 故障现象key
         */
        public final static String KEY_PHENOMENON = "phenomenon";
        
        /**
         * 维修手册ID key
         */
        public final static String KEY_MANUALID = "manualId";
        
    }
    
    /**
     * 
     * 故障原因
     *
     * <p>detailed comment
     * @author zhongruan 2016年7月12日
     * @see
     * @since 1.0
     */
    public static final class CauseFailure
    {
        /**
         * 获取故障原因接口
         */
        public static final String API_METHOD_FAULTREASON = "faultReason";
        
        /**
         * 获取故障原因接口
         */
        public static final String API_METHOD_MEADETAIL = "meaDetail";
        
        /**
         * 故障原因 key
         */
        public final static String KEY_FAULT_REASON = "fault_reason";
    }
    
    /**
     * 
     * 维修登记界面常量
     *
     * <p>detailed comment
     * @author zhongruan 2016年8月2日
     * @see
     * @since 1.0
     */
    public static final class MaintenanceRegistration
    {
        /**
         * 获取省份简称接口
         */
        public static final String API_METHOD_PROVINCE = "province";
        
        /**
         * 获取车辆信息接口
         */
        public static final String API_METHOD_CARINFO = "truck";
        
        /**
         * 客户信息页
         */
        public static final int C_INDEX_CUSTOM_INFO_TAB = 0;
        
        /**
         * 故障信息页
         */
        public static final int C_INDEX_FAULT_INFO_TAB = 1;
        
        /**
         * 维修信息页
         */
        public static final int C_INDEX_REPAIRE_INFO_TAB = 2;
        
        /**
         * 最多选择4张图片
         */
        public static final int C_MAX_PIC_NUM = 4;
        
        /**
         * 应用根文件夹名字
         */
        public static final String C_APP_ROOT_DIR = "small-test";
        
        /**
         * 保存日志的文件夹的名字
         */
        public static final String C_LOG_DIR = "log";
        
        /**
         * 保存日志的的名字
         */
        public static final String C_LOG_NAME = "log.log";
        
        /**
         * 保存图片的文件夹的名字
         */
        public static final String C_UPLOAD_IMG_DIR = "savedimg";
        
        /**
         * 保存视频的文件夹的名字
         */
        public final static String C_UPLOAD_VIDEO_DIR = "savevideo";
        
        /**
         * 下载保存文件夹的名字
         */
        public final static String C_DOWN_LOAD_DIR = "download";
        
        /**
         * 保存数据库的文件夹的名字
         */
        public final static String C_DB_DIR = "db";
        
        /**
         * 数据库名字
         */
        public final static String C_DB_NAME = "xx.db";
        
        /**
         * 图片后缀名
         */
        public final static String C_PIC_SUFFIX = ".jpg";
        
        /**
         * 图片后缀名兼容iOS
         */
        public final static String C_CAPITAL_PIC_SUFFIX = ".JPG";
        
        /**
         * 拍摄照片startActivityForResult的requestCode
         */
        public final static int C_PIC_REQUESTCODE = 0x11;
        
        /**
         * 拍摄照片视频startActivityForResult的resultCode OK
         */
        public final static int C_RESULTCODE_OK = 0x12;
        
        /**
         * 拍摄照片视频startActivityForResult的resultCode Cancle
         */
        public final static int C_RESULTCODE_CANCLE = 0x13;
        
        /**
         * 拍摄视频startActivityForResult的requestCode
         */
        public final static int C_VIDEO_REQUESTCODE = 0x14;
        
        /**
         * 预览视频startActivityForResult的requestCode
         */
        public final static int C_VIDEO_PREIVEW_REQUESTCODE = 0x15;
        
        /**
         * 预览图片startActivityForResult的requestCode
         */
        public final static int C_IMG_PREIVEW_REQUESTCODE = 0x16;
        
        /**
         * 预览视频和图片的路径的key
         */
        public final static String KEY_SELECTED_PREVIEW_PATH = "preview_path";
        
        /**
         * 是否删除预览图片和视频的key
         */
        public final static String KEY_IS_DELETE_PREVIEW = "isdelete_preview";
        
        /**
         * 是否隐藏删除按钮的key
         */
        public final static String KEY_HIDE_DELETE_BTN = "hide_btn";
        
        /**
         * 已选择的照片的key
         */
        public final static String KEY_SELECTED_PIC = "selectedImgs";

        /**
         * 已选择的视频的数量的key
         */
        public final static String KEY_SELECTED_VIDEO_COUNT = "selectedvideos";
        
        /**
         * 故障信息页
         */
        public final static String KEY_GZ_PAGE = "gz_";
        
        /**
         * 维修信息页
         */
        public final static String KEY_WX_PAGE = "wx_";
        
        /**
         * 视频文件名后缀
         */
        public final static String C_VIDEO_SUFFIX = ".mp4";
        
        /**
         * 视频文件名后缀兼容iOS
         */
        public final static String C_CAPITAL_VIDEO_SUFFIX = ".MP4";
        
        /**
         * http参数车牌号字段key
         */
        public final static String PARAMS_LICENUMBER = "liceNumber";
        
        /**
         * 由故障位置跳转到总成界面的requestCode
         */
        public final static int C_CHOSE_ASSEMB_BY_LOCATION_REQUESTCODE = 0x17;
        
        /**
         * 所选中的安装位置、一级系统、二级系统、总成数据的key
         */
        public final static String KEY_SELECTED_SYSTEM_TYPE = "selectedSsystemType";
        
        /**
         * 选取安装位置、一级系统、二级系统、总成数据的requestCode
         */
        public final static int C_CHOSE_ZONGCHENG_REQUESTCODE = 0x18;
        
        /**
         * 获取责任分类接口
         */
        public static final String API_METHOD_RESPON = "respon";
        
        /**
         * 选取责任分类requestCode
         */
        public final static int C_CHOSE_RESPON_CLASSIFICATION = 0x19;
        
        /**
         * 所选中的责任分类
         */
        public final static String KEY_SELECTED_RESPONCLASSIFICATION = "selectedResponClassification";
        
        /**
         * 最多5个配件
         */
        public final static int C_MAX_PARTS_NUM = 5;
        
        /**
         * 保存操作
         */
        public final static int C_SAVE = 0;
        
        /**
         * 提交操作
         */
        public final static int C_SUBMIT = 1;
        
        /**
         * 默认的FAULTID
         */
        public final static String C_DEFAULT_FAULTID = "0";
        
        /**
         * 申请表接口
         */
        public static final String API_METHOD_APPLY = "apply";
        
        /**
         * 默认的MEDIATYPE
         */
        public final static String C_DEFAULT_MEDIATYPE = "application/json; charset=utf-8";
        
        /**
         * isSubmit的key
         */
        public final static String KEY_ISSUBMIT = "isSubmit";
        
        /**
         * faultId的key
         */
        public final static String KEY_FAULTID = "faultId";
        
        /**
         * enclosures的key
         */
        public final static String KEY_ENCLOSURES = "enclosures";
        
        /**
         * asseList的key
         */
        public final static String KEY_ASSELIST = "asseList";
        
        /**
         * causeAnaly的key
         */
        public final static String KEY_CAUSEANALY = "causeAnaly";
        
        /**
         * companyName的key
         */
        public final static String KEY_COMPANYNAME = "companyName";
        
        /**
         * dealtStep的key
         */
        public final static String KEY_DEALTSTEP = "dealtStep";
        
        /**
         * driverName的key
         */
        public final static String KEY_DRIVERNAME = "driverName";
        
        /**
         * driverPhone的key
         */
        public final static String KEY_DRIVERPHONE = "driverPhone";
        
        /**
         * faultDesc的key
         */
        public final static String KEY_FAULTDESC = "faultDesc";
        
        /**
         * isUsed的key
         */
        public final static String KEY_ISUSED = "isUsed";
        
        /**
         * liceNumber的key
         */
        public final static String KEY_LICENUMBER = "liceNumber";
        
        /**
         * mileage的key
         */
        public final static String KEY_MILEAGE = "mileage";
        
        /**
         * modelType的key
         */
        public final static String KEY_MODELTYPE = "modelType";
        
        /**
         * modelTypeId的key
         */
        public final static String KEY_MODELTYPEID = "modelTypeId";
        
        /**
         * qualityDate的key
         */
        public final static String KEY_QUALITYDATE = "qualityDate";
        
        /**
         * repairmanArea的key
         */
        public final static String KEY_REPAIRMANAREA = "repairmanArea";
        
        /**
         * repairmanAreaId的key
         */
        public final static String KEY_REPAIRMANAREAID = "repairmanAreaId";
        
        /**
         * repairmanJobId的key
         */
        public final static String KEY_REPAIRMANJOBID = "repairmanJobId";
        
        /**
         * repairmanName的key
         */
        public final static String KEY_REPAIRMANNAME = "repairmanName";
        
        /**
         * repairmanPhone的key
         */
        public final static String KEY_REPAIRMANPHONE = "repairmanPhone";
        
        /**
         * responType的key
         */
        public final static String KEY_RESPONTYPE = "responType";
        
        /**
         * responTypeId的key
         */
        public final static String KEY_RESPONTYPEID = "responTypeId";
        
        /**
         * treatStatus的key
         */
        public final static String KEY_TREATSTATUS = "treatStatus";
        
        /**
         * truckNumber的key
         */
        public final static String KEY_TRUCKNUMBER = "truckNumber";
        
        /**
         * truckProvince的key
         */
        public final static String KEY_TRUCKPROVINCE = "truckProvince";
        
        /**
         * truckProvinceId的key
         */
        public final static String KEY_TRUCKPROVINCEID = "truckProvinceId";
        
        /**
         * truckVinId的key
         */
        public final static String KEY_TRUCKVINID = "truckVinId";
        
        /**
         * partsList的key
         */
        public final static String KEY_PARTSLIST = "partsList";
        
        /**
         * 提交文字接口
         */
        public static final String API_METHOD_FAULT = "fault";
        
        /**
         * 提交图片或视频接口
         */
        public static final String API_METHOD_SUBMITENCLOSURE = "submitEnclosure";
        
        /**
         * encloContent的key
         */
        public final static String KEY_ENCLOCONTENT = "encloContent";
        
        /**
         * encloNum的key
         */
        public final static String KEY_ENCLONUM = "encloNum";
        
    }
    
    /**
     * 
     * 个人中心
     *
     * <p>detailed comment
     * @author ztw 2016年9月8日
     * @see
     * @since 1.0
     */
    public static final class PersonCenter
    {
        /**
         * 注销接口
         */
        public static final String API_METHOD_LOGOUT = "logout";
    }
    
    /**
     * 
     * 维修记录常量
     *
     * <p>detailed comment
     * @author ztw 2016年9月8日
     * @see
     * @since 1.0
     */
    public static final class RepaireRecord
    {
        /**
         * 维修记录接口
         */
        public static final String API_METHOD_FAULTS = "faults";
        
        /**
         * 未提交
         */
        public static final int C_UNSUBMIT = 0;
        
        /**
         * 待审核
         */
        public static final int C_CHECKPENDING = 1;
        
        /**
         * 已通过
         */
        public static final int C_PASSED = 2;
        
        /**
         * 已驳回
         */
        public static final int C_REJECTED = 3;
        
        /**
         * http参数status字段key
         */
        public static final String PARAMS_STATUS = "status";
        
        /**
         * http参数pageSize字段key
         */
        public static final String PARAMS_PAGESIZE = "pageSize";
    }
    
    /**
     * 
     * 维修记录详情
     *
     * <p>detailed comment
     * @author ztw 2016年9月9日
     * @see
     * @since 1.0
     */
    public static final class RepaireRecordDetail
    {
        /**
         * 维修记录详情接口
         */
        public static final String API_METHOD_TRUFAULTDETAIL = "truFaultDetail";
        
        /**
         * 传递的MaintenanceDO的key
         */
        public static final String KEY_DELIVER_MAINTENANCEDO = "deliverMaintenanceDO";
        
        /**
         * 查看维修记录详情requestCode
         */
        public final static int C_VIEW_DETAIL_REQUEST_CODE = 0x20;
        
        /**
         * 故障信息页和维修信息页URL分隔符
         */
        public final static String C_URL_SPLIT = ",";
    }
    
    /**
     * 
     * 车辆详情
     *
     * <p>detailed comment
     * @author ztw 2016年9月20日
     * @see
     * @since 1.0
     */
    public static final class CarInfo
    {
        /**
         * 车牌号REQUESTCODE
         */
        public final static int C_CAR_NUM_REQUESTCODE = 0x16;
        
        /**
         * 传递汽车信息的Key
         */
        public final static String KEY_CAR_INFO_DETAIL = "carinfo_detail";
        
    }
    
    /**
     * 
     * 配件信息
     *
     * <p>detailed comment
     * @author ztw 2016年9月20日
     * @see
     * @since 1.0
     */
    public static final class PartsInfo
    {
        /**
         * 配件信息REQUESTCODE
         */
        public final static int C_PARTS_INFO_REQUESTCODE = 0x17;
        
        /**
         * 传递汽车配件信息的Key
         */
        public final static String KEY_PARTS_INFO_DETAIL = "part_info_detail";
        
        /**
         * http开头
         */
        public final static String C_HTTP_PREFIX = "http:";
        
    }
    
    /**
     * 
     * API
     *
     * <p>detailed comment
     * @author ztw 2016年7月4日
     * @see
     * @since 1.0
     */
    public static final class API
    {
        public final static String C_API_ROOT = "";
        
        public final static String API_ROOT_WEATHER = "https://api.heweather.com/x3/weather";
        
        /**
         * http默认连接超时时间120s
         */
        public final static int C_DEFAULT_CONN_TIMEOUT = 120;
        
        /**
         * http默认读取超时时间120s
         */
        public final static int C_DEFAULT_READ_TIMEOUT = 120;
        
        /**
         * http默认写入超时时间120s
         */
        public final static int C_DEFAULT_WRITE_TIMEOUT = 120;

        /**
         * 缓存的登录实例
         */
        public final static String KEY_CACHE_LOGIN_VO = "loginVO";
    }
    
}
