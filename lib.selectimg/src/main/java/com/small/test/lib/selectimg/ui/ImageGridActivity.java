package com.small.test.lib.selectimg.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.small.test.appstub.mvp.C;
import com.small.test.appstub.permission.PermissionFail;
import com.small.test.appstub.permission.PermissionGen;
import com.small.test.appstub.permission.PermissionSuccess;
import com.small.test.appstub.widget.dialog.AlertDialog;
import com.small.test.lib.selectimg.ImageDataSource;
import com.small.test.lib.selectimg.ImagePicker;
import com.small.test.lib.selectimg.R;
import com.small.test.lib.selectimg.adapter.ImageFolderAdapter;
import com.small.test.lib.selectimg.adapter.ImageGridAdapter;
import com.small.test.lib.selectimg.bean.ImageFolder;
import com.small.test.lib.selectimg.bean.ImageItem;
import com.small.test.lib.selectimg.view.FolderPopUpWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImageGridActivity extends ImageBaseActivity implements ImageDataSource.OnImagesLoadedListener, ImageGridAdapter.OnImageItemClickListener, ImagePicker.OnImageSelectedListener, View.OnClickListener {

    public static final int REQUEST_PERMISSION_STORAGE = 0x01;

    public static final int REQUEST_PERMISSION_CAMERA = 0x02;

    private ImagePicker imagePicker;

    private boolean isOrigin = false;  //是否选中原图

    private GridView mGridView;  //图片展示控件

    private View mFooterBar;     //底部栏

    private Button mBtnDir;      //文件夹切换按钮

    private Button mBtnPre;      //预览按钮

    private ImageFolderAdapter mImageFolderAdapter;    //图片文件夹的适配器

    private FolderPopUpWindow mFolderPopupWindow;  //ImageSet的PopupWindow

    private List<ImageFolder> mImageFolders;   //所有的图片文件夹

    private ImageGridAdapter mImageGridAdapter;  //图片九宫格展示的适配器

    private int maxImgCount = 4;               //允许选择图片最大数

    @Override
    protected int getLayoutId() {
        super.getLayoutId();
        return R.layout.activity_image_grid;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        if (titleBarView != null)
        {
            titleBarBackBtn.setVisibility(View.VISIBLE);
            backTxt.setVisibility(View.GONE);
            titleBarTitleTv.setVisibility(View.VISIBLE);
            titleBarTitleTv.setText(getString(R.string.select_pic));
            titleBarOtherTv.setVisibility(View.VISIBLE);
            titleBarOtherTv.setText(getString(R.string.finish));
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        imagePicker = ImagePicker.getInstance();
        imagePicker.clear();
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra(C.MaintenanceRegistration.KEY_SELECTED_PIC)){
                ArrayList<ImageItem> selectedImageItems = (ArrayList<ImageItem>) intent.getSerializableExtra(C.MaintenanceRegistration.KEY_SELECTED_PIC);
                imagePicker.setmSelectedImages(selectedImageItems);
            }
            imagePicker.setSelectedVideo(intent.getIntExtra(C.MaintenanceRegistration.KEY_SELECTED_VIDEO_COUNT, 0));
        }
        imagePicker.addOnImageSelectedListener(this);
        mBtnDir = (Button) findViewById(R.id.btn_dir);
        mBtnDir.setOnClickListener(this);
        mBtnPre = (Button) findViewById(R.id.btn_preview);
        mBtnPre.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.gridview);
        mFooterBar = findViewById(R.id.footer_bar);
        if (imagePicker.isMultiMode()) {
            mBtnPre.setVisibility(View.VISIBLE);
        } else {
            mBtnPre.setVisibility(View.GONE);
        }
        mImageGridAdapter = new ImageGridAdapter(this, null);
        mImageFolderAdapter = new ImageFolderAdapter(this, null);
        onImageSelected(0, null, false);
        PermissionGen.needPermission(this, STORAGEREQUESTCODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void goBack() {
        super.goBack();
        Intent intent = new Intent();
        setResult(C.MaintenanceRegistration.C_RESULTCODE_OK, intent);
        finish();
    }

    @Override
    protected void processOther() {
        ArrayList<ImageItem> selectedImageItem = imagePicker.getSelectedImages();
        if(selectedImageItem == null || selectedImageItem.isEmpty()){
            new AlertDialog(this).builder()
                    .setCanceledOnTouchOutside(true)
                    .setCancelable(true)
                    .setTitle(getString(R.string.tips))
                    .setMsg(getString(R.string.please_chose_pic))
                    .setPositiveButton(getString(R.string.sure), null)
                    .show();
        }else{
            Intent intent = new Intent();
            intent.putExtra(C.MaintenanceRegistration.KEY_SELECTED_PIC, selectedImageItem);
            setResult(C.MaintenanceRegistration.C_RESULTCODE_OK, intent);  //多选不允许裁剪裁剪，返回数据
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = STORAGEREQUESTCODE)
    public void onStorageGranted(){
        new ImageDataSource(this, null, this);
    }

    @PermissionFail(requestCode = STORAGEREQUESTCODE)
    public void onStorageNotGranted(){
        showToast("权限被禁止，无法选择本地图片");
    }

    @PermissionSuccess(requestCode = CAMERAQUESTCODE)
    public void onCameraGranted(){
        imagePicker.takePicture(this, ImagePicker.REQUEST_CODE_TAKE);
    }

    @PermissionFail(requestCode = CAMERAQUESTCODE)
    public void onCameraNotGranted(){
        showToast("权限被禁止，无法打开相机");
    }

    @Override
    protected void onDestroy() {
        imagePicker.removeOnImageSelectedListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.btn_dir) {
            if (mImageFolders == null) {
                Log.i("ImageGridActivity", "您的手机没有图片");
                return;
            }
            //点击文件夹按钮
            createPopupFolderList();
            mImageFolderAdapter.refreshData(mImageFolders);  //刷新数据
            if (mFolderPopupWindow.isShowing()) {
                mFolderPopupWindow.dismiss();
            } else {
                mFolderPopupWindow.showAtLocation(mFooterBar, Gravity.NO_GRAVITY, 0, 0);
                //默认选择当前选择的上一个，当目录很多时，直接定位到已选中的条目
                int index = mImageFolderAdapter.getSelectIndex();
                index = index == 0 ? index : index - 1;
                mFolderPopupWindow.setSelection(index);
            }
        } else if (id == R.id.btn_preview) {
            Intent intent = new Intent(ImageGridActivity.this, ImagePreviewActivity.class);
            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagePicker.getSelectedImages());
            intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
        }
    }

    /**
     * 创建弹出的ListView
     */
    private void createPopupFolderList() {
        mFolderPopupWindow = new FolderPopUpWindow(this, mImageFolderAdapter);
        mFolderPopupWindow.setOnItemClickListener(new FolderPopUpWindow.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mImageFolderAdapter.setSelectIndex(position);
                imagePicker.setCurrentImageFolderPosition(position);
                mFolderPopupWindow.dismiss();
                ImageFolder imageFolder = (ImageFolder) adapterView.getAdapter().getItem(position);
                if (null != imageFolder) {
                    mImageGridAdapter.refreshData(imageFolder.images);
                    mBtnDir.setText(imageFolder.name);
                }
                mGridView.smoothScrollToPosition(0);//滑动到顶部
            }
        });
        mFolderPopupWindow.setMargin(mFooterBar.getHeight());
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {
        this.mImageFolders = imageFolders;
        imagePicker.setImageFolders(imageFolders);
        if (imageFolders.size() == 0)
            mImageGridAdapter.refreshData(null);
        else
            mImageGridAdapter.refreshData(imageFolders.get(0).images);
        mImageGridAdapter.setOnImageItemClickListener(this);
        mGridView.setAdapter(mImageGridAdapter);
        mImageFolderAdapter.refreshData(imageFolders);
    }

    @Override
    public void onImageItemClick(View view, ImageItem imageItem, int position) {
        //根据是否有相机按钮确定位置
        position = imagePicker.isShowCamera() ? position - 1 : position;
        if (imagePicker.isMultiMode()) {
            Intent intent = new Intent(ImageGridActivity.this, ImagePreviewActivity.class);
            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagePicker.getCurrentImageFolderItems());
            intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);  //如果是多选，点击图片进入预览界面
        } else {
            imagePicker.clearSelectedImages();
            imagePicker.addSelectedImageItem(position, imagePicker.getCurrentImageFolderItems().get(position), true);
            if (imagePicker.isCrop()) {
                Intent intent = new Intent(ImageGridActivity.this, ImageCropActivity.class);
                startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
            } else {
                Intent intent = new Intent();
                intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
                setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
                finish();
            }
        }
    }

    @Override
    public void onImageSelected(int position, ImageItem item, boolean isAdd) {
        if (imagePicker.getSelectImageCount() > 0) {
            titleBarOtherTv.setText(getString(R.string.select_complete, imagePicker.getSelectImageCount(), imagePicker.getSelectLimit()-imagePicker.getSelectedVideo()));
            mBtnPre.setEnabled(true);
        } else {
            titleBarOtherTv.setText(getString(R.string.complete));
            mBtnPre.setEnabled(false);
        }
        mBtnPre.setText(getResources().getString(R.string.preview_count, imagePicker.getSelectImageCount()));
        mImageGridAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == ImagePicker.RESULT_CODE_BACK) {
                isOrigin = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            } else {
                //从拍照界面返回
                //点击 X , 没有选择照片
                if (data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) == null) {
                    setResult(C.MaintenanceRegistration.C_RESULTCODE_OK, data);
                    finish();
                } else {
                    //说明是从裁剪页面过来的数据，直接返回就可以
                    setResult(ImagePicker.RESULT_CODE_ITEMS, data);
                    finish();
                }
            }
        } else {
            //如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
            if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_TAKE) {
                //发送广播通知图片增加了
                ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());
                ImageItem imageItem = new ImageItem();
                imageItem.isCamera = false;
                imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
                //imagePicker.clearSelectedImages();
                imagePicker.addSelectedImageItem(0, imageItem, true);
                mImageGridAdapter.refreshData((ArrayList<ImageItem>) mImageGridAdapter.getLists());
                if (imagePicker.isCrop()) {
                    Intent intent = new Intent(ImageGridActivity.this, ImageCropActivity.class);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
                }
            }
        }
    }
}