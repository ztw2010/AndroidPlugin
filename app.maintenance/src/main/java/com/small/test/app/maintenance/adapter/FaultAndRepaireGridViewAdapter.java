package com.small.test.app.maintenance.adapter;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.small.test.app.maintenance.R;
import com.small.test.app.maintenance.maintenance.MaintenanceRegistrationActivity;
import com.small.test.app.maintenance.maintenance.MaintenanceRegistrationContract;
import com.small.test.appstub.adapter.AdapterBase;
import com.small.test.appstub.adapter.ViewHolder;
import com.small.test.appstub.loadimg.ImageLoader;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.Mvp;
import com.small.test.appstub.utils.device.ImageUtils;
import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.Utils;
import com.small.test.lib.selectimg.bean.ImageItem;

/**
 * 
 * 故障信息页Adapter
 *
 * <p>detailed comment
 * @author ztw 2016年8月12日
 * @see
 * @since 1.0
 */
public class FaultAndRepaireGridViewAdapter extends AdapterBase<ImageItem>
{
    private LayoutInflater inflater;
    
    private ImageItem imageAddVO;//加号图标
    
    private MaintenanceRegistrationContract.View view;

    private int mSize = 0;

    private ImageLoader imageLoader;

    public FaultAndRepaireGridViewAdapter(LayoutInflater inflater)
    {
        this.inflater = inflater;
        this.imageAddVO = new ImageItem();
        view = (MaintenanceRegistrationContract.View) Mvp.getInstance().getView(MaintenanceRegistrationActivity.class);
        mSize = Utils.getImageItemWidth(inflater.getContext());
        imageLoader = ImagePicker.getInstance().getImageLoader();
    }
    
    @Override
    protected View getExView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_grid_maintanance, parent, false);
        }
        ImageView addImgView = ViewHolder.get(convertView, R.id.add_pic_img);
        ImageView imgShow = ViewHolder.get(convertView, R.id.img_show);
        ImageView playImg = ViewHolder.get(convertView, R.id.video_player);
        ImageView failedImg = ViewHolder.get(convertView, R.id.failed_img);
        String path = lists.get(position).path;
        if (TextUtils.isEmpty(path))
            path = " ";
        int size = lists.size();
        if (lists.contains(imageAddVO))
        {
            if (size == 1)
            {
                addImgView.setVisibility(View.VISIBLE);
                imgShow.setVisibility(View.GONE);
                playImg.setVisibility(View.GONE);
            }
            else if (position == size - 1)
            {
                addImgView.setVisibility(View.VISIBLE);
                imgShow.setVisibility(View.GONE);
                playImg.setVisibility(View.GONE);
            }
            else
            {
                addImgView.setVisibility(View.GONE);
                imgShow.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(path))
                {
                    if (path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX)
                            || path.endsWith(C.MaintenanceRegistration.C_CAPITAL_VIDEO_SUFFIX))
                    {
                        playImg.setVisibility(View.VISIBLE);
                        Bitmap bitmap = ImageUtils.getVideoThumbnail(path, mSize, mSize, MediaStore.Images.Thumbnails.MINI_KIND);
                        imgShow.setImageBitmap(bitmap);
                    }
                    else
                    {
                        playImg.setVisibility(View.GONE);
                        imageLoader.displayImage(inflater.getContext(), path, imgShow, mSize, mSize);
                    }
                }
            }
        }
        else
        {
            addImgView.setVisibility(View.GONE);
            imgShow.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(path))
            {
                if (path.endsWith(C.MaintenanceRegistration.C_VIDEO_SUFFIX)
                        || path.endsWith(C.MaintenanceRegistration.C_CAPITAL_VIDEO_SUFFIX))
                {
                    playImg.setVisibility(View.VISIBLE);
                    Bitmap bitmap = ImageUtils.getVideoThumbnail(path, mSize, mSize, MediaStore.Images.Thumbnails.MINI_KIND);
                    imgShow.setImageBitmap(bitmap);
                }
                else
                {
                    imageLoader.displayImage(inflater.getContext(), path, imgShow, mSize, mSize);
                    playImg.setVisibility(View.GONE);
                }
            }
        }
        failedImg.setVisibility(View.GONE);
        return convertView;
    }
}
