package com.small.test.app.maintenance.maintenance;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.test.app.maintenance.R;
import com.small.test.app.maintenance.adapter.FaultAndRepaireGridViewAdapter;
import com.small.test.app.maintenance.maintenance.data.ProvinceVO;
import com.small.test.appstub.log.L;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.MvpActivity;
import com.small.test.appstub.permission.PermissionFail;
import com.small.test.appstub.permission.PermissionGen;
import com.small.test.appstub.permission.PermissionSuccess;
import com.small.test.appstub.systeminit.SystemInit;
import com.small.test.appstub.utils.DateUtils;
import com.small.test.appstub.utils.device.ImageUtils;
import com.small.test.appstub.widget.dialog.ActionSheetDialog;
import com.small.test.appstub.widget.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.small.test.appstub.widget.dialog.ActionSheetDialog.SheetItemColor;
import com.small.test.appstub.widget.dialog.AlertDialog;
import com.small.test.appstub.widget.pagerslidingtabstrip.PagerSlidingTabStrip;
import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.bean.ImageItem;
import com.small.test.lib.selectimg.ui.ImageGridActivity;
import com.small.test.lib.selectimg.ui.ImagePreviewDelActivity;
import com.small.test.lib.video.playvideo.VideoPreViewActivity;
import com.small.test.lib.video.record.CaptureConfiguration;
import com.small.test.lib.video.record.PredefinedCaptureConfigurations;
import com.small.test.lib.video.record.VideoCaptureActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vov.vitamio.LibsChecker;

/**
 * Created by ztw on 2016/12/15.
 * 维修登记插件
 */

public class MaintenanceRegistrationActivity extends MvpActivity<MaintenanceRegistrationPresenter> implements MaintenanceRegistrationContract.View{

    /**
     * 读取SDCard requestCode
     */
    public static final int STORAGE_REQUESTCODE = 0x01;

    /**
     * camera requestCode
     */
    public static final int CAMERA_AUDIO_QUESTCODE = 0x02;

    private ViewPager navigationVP;

    private Button submitBtn;

    private PagerSlidingTabStrip navigationPSTS;

    private MyPagerAdapter pagerAdapter;

    private List<View> pagerSlidingTabStrips = new ArrayList<View>();

    /** begin  CustomerInfoView    **/
    private EditText customerNameEt;

    private TextView customerVIN;

    private EditText customerMileageEt;

    private EditText customerLicenumEt;

    public TextView rejectDescTv;

    private TextView customerGuarantee;

    private LinearLayout rejectDescContainer;

    private TextView customerCompany;

    private FrameLayout getVimContainer;

    private FrameLayout provinceSimpleNameFl;

    private TextView provinceShortNameTv;

    private TextView customerClassify;

    private EditText customerPhoneEt;

    private LinearLayout carInfoContainer;

    /** end  CustomerInfoView    **/

    /** begin  FaultInfoView    **/
    private RelativeLayout faultinfoClickContainer;

    private RelativeLayout responsibleClassificationContainer;

    private FrameLayout responsibility_container;

    private TextView responsibleClassificationTv;

    private EditText faultDisplayEt;

    private GridView faultShowGridview;

    private FaultAndRepaireGridViewAdapter faultGridViewAdapter;

    private LinearLayout faultinfoDetailContainer;

    private LinearLayout faultLocationTipContainer;

    private EditText faultDescritionEt;

    /** end  FaultInfoView    **/

    /** begin  RepaireInfoView    **/
    private EditText decripLocationEt;

    private ImageView noImg;

    private ImageView faultYesImg;

    private RelativeLayout noContainer;

    private RelativeLayout yesContainer;

    private LinearLayout maintenanceInforContainer;

    private GridView repaireShowGridview;

    private FaultAndRepaireGridViewAdapter repaireGridViewAdapter;

    private ImageView yesImg;

    private ImageView faultNoImg;

    private RelativeLayout faultNoContainer;

    private RelativeLayout faultYesConatainer;

    private RelativeLayout maintenanceClickContainer;

    private View noChoseView;

    /** end  RepaireInfoView    **/

    private List<String> titleStr = null;

    private LayoutInflater inflater = null;

    private int TAB_INDEX = C.MaintenanceRegistration.C_INDEX_CUSTOM_INFO_TAB;

    private ImageItem imageAddVO = new ImageItem();//加号图标

    @Override
    public void showLoading() {
        svProgressHUD.showWithStatus(getString(R.string.loading_data));
    }

    @Override
    public void hideLoading() {
        svProgressHUD.dismissImmediately();
    }

    @Override
    public void showTip(String content) {
        svProgressHUD.showInfoWithStatus(content);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maintenance_registration;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        inflater = getLayoutInflater();
        titleStr = new ArrayList<String>()
        {
            private static final long serialVersionUID = -2588301075699590317L;
            {
                add(getString(R.string.customer_info));
                add(getString(R.string.fault_mess));
                add(getString(R.string.repaire_info));
            }
        };
        navigationVP = (ViewPager) findViewById(R.id.navigation_pager);
        navigationVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TAB_INDEX = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        submitBtn = (Button) findViewById(R.id.submit_info_btn);
        navigationPSTS = (PagerSlidingTabStrip) findViewById(R.id.navigation_tabs);
        /**
         * init customer_info
         */
        View cutomerView = inflater.inflate(R.layout.fragment_customer_info, null);
        rejectDescContainer = (LinearLayout)cutomerView.findViewById(R.id.id_reject_ll);
        rejectDescTv = (TextView)cutomerView.findViewById(R.id.id_reject_tv);
        customerNameEt = (EditText)cutomerView.findViewById(R.id.id_customer_textname);
        customerPhoneEt = (EditText)cutomerView.findViewById(R.id.id_customer_textphone);
        customerLicenumEt = (EditText)cutomerView.findViewById(R.id.id_customer_textlicenum);
        customerMileageEt = (EditText)cutomerView.findViewById(R.id.id_customer_textmileage);
        customerVIN = (TextView)cutomerView.findViewById(R.id.id_customer_textvin);
        customerClassify = (TextView)cutomerView.findViewById(R.id.id_customer_textclassify);
        customerCompany = (TextView)cutomerView.findViewById(R.id.id_customer_textcompany);
        customerGuarantee = (TextView)cutomerView.findViewById(R.id.id_customer_textguarantee);
        provinceSimpleNameFl = (FrameLayout)cutomerView.findViewById(R.id.simple_name_fl);
        provinceSimpleNameFl.setOnClickListener(this);
        provinceShortNameTv = (TextView)cutomerView.findViewById(R.id.province_short_name_txt);
        getVimContainer = (FrameLayout)cutomerView.findViewById(R.id.get_vim_container);
        getVimContainer.setOnClickListener(this);
        carInfoContainer = (LinearLayout)cutomerView.findViewById(R.id.car_info_container);
        /**
         * init fault_info
         */
        View faultinfoView = inflater.inflate(R.layout.fragment_fault_info, null);
        faultinfoClickContainer = (RelativeLayout)faultinfoView.findViewById(R.id.faultinfo_click_container);
        faultinfoClickContainer.setOnClickListener(this);
        responsibleClassificationContainer =
                (RelativeLayout)faultinfoView.findViewById(R.id.responsible_classification_container);
        responsibleClassificationContainer.setOnClickListener(this);
        faultinfoDetailContainer = (LinearLayout)faultinfoView.findViewById(R.id.faultinfo_container);
        responsibleClassificationTv = (TextView)faultinfoView.findViewById(R.id.responsible_classification_txt);
        faultDescritionEt = (EditText)faultinfoView.findViewById(R.id.fault_descrition_et);
        faultDisplayEt = (EditText)faultinfoView.findViewById(R.id.fault_display_et);
        faultGridViewAdapter = new FaultAndRepaireGridViewAdapter(inflater);
        faultShowGridview = (GridView)faultinfoView.findViewById(R.id.fault_show_gridview);
        faultShowGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        faultShowGridview.setAdapter(faultGridViewAdapter);
        List<ImageItem> imageVOs = new ArrayList<ImageItem>();
        imageVOs.add(imageAddVO);
        faultGridViewAdapter.setLists(imageVOs);
        faultGridViewAdapter.notifyDataSetChanged();
        faultShowGridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                List<ImageItem> selectedImageItems = faultGridViewAdapter.getLists();
                ImageItem currentImageItem = selectedImageItems.get(position);
                String path = currentImageItem.path;
                if (currentImageItem.isCamera)
                {
                    showChoicePopwindow();
                }
                else
                {
                    if (path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX))
                    {
                        if (path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX))
                        {
                            initializaVitamino(path);
                        }
                    }
                    else
                    {
                        ArrayList<ImageItem> removeAddImageItems = new ArrayList<ImageItem>();
                        for(ImageItem imageItem : selectedImageItems){
                            if(!imageItem.isCamera && !TextUtils.isEmpty(imageItem.path) && !imageItem.path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX)){
                                removeAddImageItems.add(imageItem.copyNew());
                            }
                        }
                        Intent intent = new Intent(MaintenanceRegistrationActivity.this, ImagePreviewDelActivity.class);
                        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, removeAddImageItems.indexOf(currentImageItem));
                        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, removeAddImageItems);
                        startActivityForResult(intent, C.MaintenanceRegistration.C_IMG_PREIVEW_REQUESTCODE);
                    }
                }
            }
        });
        faultLocationTipContainer = (LinearLayout)faultinfoView.findViewById(R.id.fault_location_tip);
        /**
         * init maintenance_infor
         */
        View maintenanceInforView = inflater.inflate(R.layout.fragment_maintenance_infor, null);
        faultYesConatainer = (RelativeLayout)maintenanceInforView.findViewById(R.id.fault_yes_container);
        faultYesConatainer.setOnClickListener(this);
        faultYesImg = (ImageView)maintenanceInforView.findViewById(R.id.fault_yes_img);
        faultNoImg = (ImageView)maintenanceInforView.findViewById(R.id.fault_no_img);
        noChoseView = maintenanceInforView.findViewById(R.id.no_chose_view);
        noChoseView.setVisibility(View.GONE);
        faultNoContainer = (RelativeLayout)maintenanceInforView.findViewById(R.id.fault_no_container);
        faultNoContainer.setOnClickListener(this);
        yesContainer = (RelativeLayout)maintenanceInforView.findViewById(R.id.yes_container);
        yesContainer.setOnClickListener(this);
        noContainer = (RelativeLayout)maintenanceInforView.findViewById(R.id.no_container);
        noContainer.setOnClickListener(this);
        yesImg = (ImageView)maintenanceInforView.findViewById(R.id.yes_img);
        noImg = (ImageView)maintenanceInforView.findViewById(R.id.no_img);
        maintenanceClickContainer = (RelativeLayout)maintenanceInforView.findViewById(R.id.maintenance_click_container);
        maintenanceClickContainer.setOnClickListener(this);
        maintenanceInforContainer = (LinearLayout)maintenanceInforView.findViewById(R.id.maintenance_infor_container);
        decripLocationEt = (EditText)maintenanceInforView.findViewById(R.id.decrip_location_et);
        repaireShowGridview = (GridView)maintenanceInforView.findViewById(R.id.repare_info_img_upload_gv);
        repaireShowGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        repaireGridViewAdapter = new FaultAndRepaireGridViewAdapter(inflater);
        repaireShowGridview.setAdapter(repaireGridViewAdapter);
        List<ImageItem> repareImageVOs = new ArrayList<ImageItem>();
        repareImageVOs.add(imageAddVO);
        repaireGridViewAdapter.setLists(repareImageVOs);
        repaireGridViewAdapter.notifyDataSetChanged();
        repaireShowGridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                List<ImageItem> selectedImageItems = repaireGridViewAdapter.getLists();
                ImageItem currentImageItem = selectedImageItems.get(position);
                String path = currentImageItem.path;
                if (currentImageItem.isCamera)
                {
                    showChoicePopwindow();
                }
                else
                {
                    if (path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX))
                    {
                        initializaVitamino(path);
                    }
                    else
                    {
                        ArrayList<ImageItem> removeAddImageItems = new ArrayList<ImageItem>();
                        for(ImageItem imageItem : selectedImageItems){
                            if(!imageItem.isCamera && !TextUtils.isEmpty(imageItem.path) && !imageItem.path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX)){
                                removeAddImageItems.add(imageItem.copyNew());
                            }
                        }
                        Intent intent = new Intent(MaintenanceRegistrationActivity.this, ImagePreviewDelActivity.class);
                        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, removeAddImageItems.indexOf(currentImageItem));
                        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, removeAddImageItems);
                        startActivityForResult(intent, C.MaintenanceRegistration.C_IMG_PREIVEW_REQUESTCODE);
                    }
                }
            }
        });
        pagerSlidingTabStrips.add(cutomerView);
        pagerSlidingTabStrips.add(faultinfoView);
        pagerSlidingTabStrips.add(maintenanceInforView);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), pagerSlidingTabStrips);
        pagerAdapter.notifyDataSetChanged();
        navigationVP.setAdapter(pagerAdapter);
        navigationVP.setCurrentItem(0);
        navigationVP.setOffscreenPageLimit(3);
        navigationPSTS.setViewPager(navigationVP);
    }

    /**
     * 初始化视频播放so
     */
    private void initializaVitamino(final String videoPath){
        if (!LibsChecker.checkVitamioLibs(this)){
            L.d("video player not initialized");
            mPresenter.initializaVitamino(new MaintenanceRegistrationContract.Presenter.OnInitListener() {
                @Override
                public void onStart() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoading();
                        }
                    });
                }

                @Override
                public void onError() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideLoading();
                            new AlertDialog(MaintenanceRegistrationActivity.this)
                                    .builder()
                                    .setCancelable(false)
                                    .setCanceledOnTouchOutside(false)
                                    .setMsg(getString(R.string.init_video_player_error))
                                    .setPositiveButton(getString(R.string.sure), null)
                                    .show();
                        }
                    });
                }

                @Override
                public void onFinish() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideLoading();
                            Intent intent = new Intent(MaintenanceRegistrationActivity.this, VideoPreViewActivity.class);
                            intent.putExtra(C.MaintenanceRegistration.KEY_SELECTED_PREVIEW_PATH, videoPath);
                            intent.putExtra(C.MaintenanceRegistration.KEY_HIDE_DELETE_BTN, false);
                            startActivityForResult(intent, C.MaintenanceRegistration.C_VIDEO_PREIVEW_REQUESTCODE);
                        }
                    });
                }
            });
        }else{
            Intent intent = new Intent(MaintenanceRegistrationActivity.this, VideoPreViewActivity.class);
            intent.putExtra(C.MaintenanceRegistration.KEY_SELECTED_PREVIEW_PATH, videoPath);
            intent.putExtra(C.MaintenanceRegistration.KEY_HIDE_DELETE_BTN, false);
            startActivityForResult(intent, C.MaintenanceRegistration.C_VIDEO_PREIVEW_REQUESTCODE);
        }
    }

    @Override
    protected void initTitle() {
        if (titleBarView != null)
        {
            titleBarBackBtn.setVisibility(View.VISIBLE);
            backTxt.setVisibility(View.GONE);
            titleBarTitleTv.setVisibility(View.VISIBLE);
            titleBarTitleTv.setText(getString(R.string.repair_register));
            titleBarOtherTv.setVisibility(View.VISIBLE);
            titleBarOtherTv.setText(getString(R.string.save));
        }
    }

    @Override
    public IBaseView getBaseView() {
        return this;
    }

    private class MyPagerAdapter extends PagerAdapter
    {
        private List<View> views;

        public MyPagerAdapter(FragmentManager fm, List<View> views)
        {
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            ((ViewPager)container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            ((ViewPager)container).addView(views.get(position), 0);
            return views.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return titleStr.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public int getCount()
        {
            return views.size();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = STORAGE_REQUESTCODE)
    public void onStorageGranted(){
        Intent intent = new Intent(MaintenanceRegistrationActivity.this, ImageGridActivity.class);
        ArrayList<ImageItem> copyImageVOs = new ArrayList<ImageItem>();//去掉+图标;
        List<ImageItem> imageVOs = null;
        if (isFaultPage())
        {
            imageVOs = faultGridViewAdapter.getLists();
        }
        else if (isRepairePage())
        {
            imageVOs = repaireGridViewAdapter.getLists();
        }
        int videoCount = 0;
        for (ImageItem cImageVO : imageVOs)
        {
            if (!cImageVO.isCamera && !TextUtils.isEmpty(cImageVO.path) && !ImageUtils.isVideoFile(cImageVO.path))
            {
                copyImageVOs.add(cImageVO);
            }
            else if(ImageUtils.isVideoFile(cImageVO.path))
            {
                videoCount += 1;
            }
        }
        intent.putExtra(C.MaintenanceRegistration.KEY_SELECTED_PIC, copyImageVOs);
        intent.putExtra(C.MaintenanceRegistration.KEY_SELECTED_VIDEO_COUNT, videoCount);
        startActivityForResult(intent, C.MaintenanceRegistration.C_PIC_REQUESTCODE);
    }

    @PermissionFail(requestCode = STORAGE_REQUESTCODE)
    public void onStorageNotGranted(){
        showTip(getString(R.string.sdcar_permission_denied));
    }

    @PermissionSuccess(requestCode = CAMERA_AUDIO_QUESTCODE)
    public void onCameraGranted(){
        final CaptureConfiguration config = new CaptureConfiguration(PredefinedCaptureConfigurations.CaptureResolution.RES_1080P, PredefinedCaptureConfigurations.CaptureQuality.HIGH,
                10, CaptureConfiguration.NO_FILESIZE_LIMIT, true);
        final Intent intent = new Intent(MaintenanceRegistrationActivity.this, VideoCaptureActivity.class);
        intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION, config);
        String filePath = "";
        String fileName = "";
        final String dateStamp = DateUtils.format(new Date(), DateUtils.DATE_FORMAT3);
        if (isFaultPage())
        {
            fileName = String.format("%s%s%s",
                    C.MaintenanceRegistration.KEY_GZ_PAGE,
                    dateStamp,
                    C.MaintenanceRegistration.C_VIDEO_SUFFIX);
        }
        else if (isRepairePage())
        {
            fileName = String.format("%s%s%s",
                    C.MaintenanceRegistration.KEY_WX_PAGE,
                    dateStamp,
                    C.MaintenanceRegistration.C_VIDEO_SUFFIX);
        }
        File file = new File(SystemInit.getInstance().getUpLoadVideoFile(), fileName);
        filePath = file.getAbsolutePath();
        intent.putExtra(VideoCaptureActivity.EXTRA_FILE_PATH, filePath);
        startActivityForResult(intent, C.MaintenanceRegistration.C_VIDEO_REQUESTCODE);
    }

    @PermissionFail(requestCode = CAMERA_AUDIO_QUESTCODE)
    public void onCameraNotGranted(){
        showTip(getString(R.string.camera_permission_denied));
    }

    private void showChoicePopwindow(){
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem(getString(R.string.video), SheetItemColor.Black,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                PermissionGen.needPermission(MaintenanceRegistrationActivity.this, CAMERA_AUDIO_QUESTCODE, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                            }
                        })
                .addSheetItem(getString(R.string.album), SheetItemColor.Black,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                PermissionGen.needPermission(MaintenanceRegistrationActivity.this, STORAGE_REQUESTCODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                        })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 是否是故障信息页
     * @return
     */
    @Override
    public boolean isFaultPage()
    {
        return TAB_INDEX == C.MaintenanceRegistration.C_INDEX_FAULT_INFO_TAB ? true : false;
    }

    @Override
    public void refreshFaultPageAdapter(List<ImageItem> imageItems) {
        faultGridViewAdapter.setLists(imageItems);
        faultGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public List<ImageItem> getFaultPageAdapterList() {
        return faultGridViewAdapter.getLists();
    }

    /**
     * 是否是维修信息页
     * @return
     */
    @Override
    public boolean isRepairePage()
    {
        return TAB_INDEX == C.MaintenanceRegistration.C_INDEX_REPAIRE_INFO_TAB ? true : false;
    }

    @Override
    public void refreshRepairePageAdapter(List<ImageItem> imageItems) {
        repaireGridViewAdapter.setLists(imageItems);
        repaireGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public List<ImageItem> getRepairePageAdapterList() {
        return repaireGridViewAdapter.getLists();
    }

    @Override
    public void onProvinceSelected(ProvinceVO provinceVO) {

    }

    @Override
    public void onProvincePull2Refresh() {

    }


    @Override
    protected void goBack() {
        super.goBack();
        finish();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        switch (id){
            case R.id.simple_name_fl:

                break;
        }
    }
}
