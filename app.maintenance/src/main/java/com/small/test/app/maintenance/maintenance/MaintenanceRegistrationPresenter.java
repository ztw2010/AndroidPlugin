package com.small.test.app.maintenance.maintenance;

import android.content.Intent;
import android.text.TextUtils;

import com.small.test.app.maintenance.R;
import com.small.test.app.maintenance.maintenance.data.ProvinceVO;
import com.small.test.app.maintenance.maintenance.data.source.MaintenanceregistrationRepository;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.ErrorMessageFactory;
import com.small.test.appstub.mvp.MvpPresenter;
import com.small.test.appstub.mvp.UseCase;
import com.small.test.appstub.utils.device.FileUtils;
import com.small.test.appstub.utils.device.ImageUtils;
import com.small.test.appstub.utils.device.ThreadPoolUtils;
import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.bean.ImageItem;
import com.small.test.lib.video.record.VideoCaptureActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.vov.vitamio.Vitamio;

import static com.small.test.appstub.mvp.C.MaintenanceRegistration.C_VIDEO_PREIVEW_REQUESTCODE;

public class MaintenanceRegistrationPresenter
    extends MvpPresenter<MaintenanceregistrationRepository, MaintenanceRegistrationContract.View>
    implements MaintenanceRegistrationContract.Presenter
{
    private ImageItem imageAddVO = new ImageItem();//加号图标


    @Override
    protected void initUsecase()
    {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isFaultPage = iView.isFaultPage();
        boolean isRepairePage = iView.isRepairePage();
        if (resultCode == C.MaintenanceRegistration.C_RESULTCODE_OK){
            if (requestCode == C.MaintenanceRegistration.C_PIC_REQUESTCODE && data != null){//选取照片回调
                ArrayList<ImageItem> selectedImageItem = (ArrayList<ImageItem>) data.getSerializableExtra(C.MaintenanceRegistration.KEY_SELECTED_PIC);
                List<ImageItem> originIamgeItems = null;
                if(selectedImageItem != null)
                {
                    if (isFaultPage)
                    {
                        originIamgeItems = iView.getFaultPageAdapterList();
                    }
                    else if (isRepairePage)
                    {
                        originIamgeItems = iView.getRepairePageAdapterList();
                    }
                    List<ImageItem> videoImageItems = new ArrayList<ImageItem>();
                    if(originIamgeItems != null){
                        for(ImageItem cImageItem : originIamgeItems){
                            if(ImageUtils.isVideoFile(cImageItem.path)){
                                videoImageItems.add(cImageItem);
                            }
                        }
                        originIamgeItems.clear();
                        originIamgeItems.addAll(videoImageItems);
                        originIamgeItems.addAll(selectedImageItem);
                        if(originIamgeItems.size() < C.MaintenanceRegistration.C_MAX_PIC_NUM && !originIamgeItems.contains(imageAddVO)){
                            originIamgeItems.add(imageAddVO);
                        }else if(originIamgeItems.size() >= C.MaintenanceRegistration.C_MAX_PIC_NUM && originIamgeItems.contains(imageAddVO)){
                            originIamgeItems.remove(imageAddVO);
                        }
                        if (isFaultPage)
                        {
                            iView.refreshFaultPageAdapter(originIamgeItems);
                        }
                        else if (isRepairePage)
                        {
                            iView.refreshRepairePageAdapter(originIamgeItems);
                        }
                    }

                }
            }
            else if (data != null && requestCode == C.MaintenanceRegistration.C_IMG_PREIVEW_REQUESTCODE)//图片预览
            {
                List<ImageItem> imageItems = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                List<ImageItem> delImageItems = new ArrayList<ImageItem>();
                List<ImageItem> sourceImageItems = isFaultPage ? iView.getFaultPageAdapterList() : iView.getRepairePageAdapterList();
                for(ImageItem imageItem : sourceImageItems)
                {
                    if(!imageItems.contains(imageItem) && !TextUtils.isEmpty(imageItem.path) && !imageItem.path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX))
                    {
                        delImageItems.add(imageItem);
                    }
                }
                for(ImageItem imageItem : delImageItems)
                {
                    sourceImageItems.remove(imageItem);
                }
                if (sourceImageItems.size() == C.MaintenanceRegistration.C_MAX_PIC_NUM + 1 && sourceImageItems.contains(imageAddVO))
                {
                    sourceImageItems.remove(imageAddVO);
                }
                else if(sourceImageItems.size() < C.MaintenanceRegistration.C_MAX_PIC_NUM && !sourceImageItems.contains(imageAddVO))
                {
                    sourceImageItems.add(sourceImageItems.size(), imageAddVO);
                }
                if (isFaultPage)
                {
                    iView.refreshFaultPageAdapter(sourceImageItems);
                }
                else if (isRepairePage)
                {
                    iView.refreshRepairePageAdapter(sourceImageItems);
                }
            }else if(data != null && requestCode == C_VIDEO_PREIVEW_REQUESTCODE){//视频预览
                String deletePath = data.getStringExtra(C.MaintenanceRegistration.KEY_SELECTED_PREVIEW_PATH);
                if(!TextUtils.isEmpty(deletePath)){
                    List<ImageItem> sourceImageItems = isFaultPage ? iView.getFaultPageAdapterList() : iView.getRepairePageAdapterList();
                    if(sourceImageItems != null){
                        Iterator<ImageItem> imageItemIterator = sourceImageItems.iterator();
                        while (imageItemIterator.hasNext()){
                            ImageItem imageItem = imageItemIterator.next();
                            if(deletePath.equals(imageItem.path)){
                                imageItemIterator.remove();
                            }
                        }
                        if(sourceImageItems.size() < C.MaintenanceRegistration.C_MAX_PIC_NUM && !sourceImageItems.contains(imageAddVO)){
                            sourceImageItems.add(sourceImageItems.size(), imageAddVO);
                        }
                        if (isFaultPage)
                        {
                            iView.refreshFaultPageAdapter(sourceImageItems);
                        }
                        else if (isRepairePage)
                        {
                            iView.refreshRepairePageAdapter(sourceImageItems);
                        }
                    }
                }
            }
            else if (requestCode == C.MaintenanceRegistration.C_VIDEO_REQUESTCODE && data != null)//录制视频后点击√号即保存
            {
                String filename = data.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME);
                if (!TextUtils.isEmpty(filename))
                {
                    File videoFile = FileUtils.getFileByPath(filename);
                    if (isFaultPage)
                    {
                        List<ImageItem> imageVOs = iView.getFaultPageAdapterList();
                        int position = imageVOs.indexOf(imageAddVO);
                        imageVOs.add(position, new ImageItem(videoFile.lastModified(), 0, "", videoFile.getName(), videoFile.getAbsolutePath(), videoFile.length(), 0));
                        if (imageVOs.size() == C.MaintenanceRegistration.C_MAX_PIC_NUM + 1
                                && imageVOs.contains(imageAddVO))
                        {
                            imageVOs.remove(imageAddVO);
                        }
                        iView.refreshFaultPageAdapter(imageVOs);
                    }
                    else if (isRepairePage)
                    {
                        List<ImageItem> imageVOs = iView.getRepairePageAdapterList();
                        int position = imageVOs.indexOf(imageAddVO);
                        imageVOs.add(position, new ImageItem(videoFile.lastModified(), 0, "", filename, videoFile.getAbsolutePath(), videoFile.length(), 0));
                        if (imageVOs.size() == C.MaintenanceRegistration.C_MAX_PIC_NUM + 1
                                && imageVOs.contains(imageAddVO))
                        {
                            imageVOs.remove(imageAddVO);
                        }
                        iView.refreshRepairePageAdapter(imageVOs);
                    }
                }
            }
        }
        else if (resultCode == C.MaintenanceRegistration.C_RESULTCODE_CANCLE && data != null)//录制视频后点击X号即不保存
        {
            String filePath = data.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME);
            if (!TextUtils.isEmpty(filePath))
            {
                FileUtils.createFileByDeleteOldFile(filePath);
            }
        }
    }

    @Override
    public void initializaVitamino(final OnInitListener onInitListener) {
        new ThreadPoolUtils(ThreadPoolUtils.Type.SingleThread, 0).execute(new Runnable() {

            @Override
            public void run() {
                try{
                    onInitListener.onStart();
                    Vitamio.initialize(mContext, R.raw.libarm);
                    onInitListener.onFinish();
                } catch (Exception e){
                    onInitListener.onError();
                }
            }
        });
    }

    @Override
    public void getProvinces(String jobId, boolean isRefresh) {
        if (!TextUtils.isEmpty(jobId))
        {
            iView.showLoading();
            mUseCaseHandler.execute(null,
                    new GetProvinceUseCase.RequestValues(jobId, isRefresh),
                    new UseCase.UseCaseCallback<GetProvinceUseCase.ResponseValue>()
                    {
                        @Override
                        public void onSuccess(GetProvinceUseCase.ResponseValue response)
                        {
                            List<ProvinceVO> provinceVOs = response.getProvinceVO();
                            if (provinceVOs == null || provinceVOs.isEmpty())
                            {
                                //iView.onProvincesNoDatas();
                            }
                            else
                            {
                            }
                            iView.hideLoading();
                        }

                        @Override
                        public void onError(Exception exception)
                        {
                            iView.hideLoading();
                            String errorMsg = ErrorMessageFactory.create(mContext, exception);
                            if (!TextUtils.isEmpty(errorMsg))
                            {
                                iView.showTip(errorMsg);
                            }
                        }
                    });
        }
    }
}
