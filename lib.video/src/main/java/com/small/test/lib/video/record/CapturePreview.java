package com.small.test.lib.video.record;

import android.view.SurfaceHolder;

import com.small.test.appstub.log.L;

import java.io.IOException;

public class CapturePreview implements SurfaceHolder.Callback
{
    private boolean mPreviewRunning = false;
    
    private final CapturePreviewInterface mInterface;
    
    public final CameraWrapper mCameraWrapper;

    public CapturePreview(CapturePreviewInterface capturePreviewInterface, CameraWrapper cameraWrapper,
        SurfaceHolder holder)
    {
        mInterface = capturePreviewInterface;
        mCameraWrapper = cameraWrapper;
        
        initalizeSurfaceHolder(holder);
    }
    
    @SuppressWarnings("deprecation")
    private void initalizeSurfaceHolder(final SurfaceHolder surfaceHolder)
    {
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // Necessary for older API's
    }
    
    @Override
    public void surfaceCreated(final SurfaceHolder holder)
    {
    }
    
    @Override
    public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height)
    {
        if (mPreviewRunning)
        {
            try
            {
                mCameraWrapper.stopPreview();
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
        }
        
        try
        {
            mCameraWrapper.configureForPreview(width, height);
            L.d("Configured camera for preview in surface of " + width + " by " + height);
        }
        catch (final RuntimeException e)
        {
            L.e("Failed to show preview - invalid parameters set to camera preview", e);
            mInterface.onCapturePreviewFailed();
            return;
        }
        
        try
        {
            mCameraWrapper.enableAutoFocus();
        }
        catch (final RuntimeException e)
        {
            L.e("AutoFocus not available for preview", e);
        }
        
        try
        {
            mCameraWrapper.startPreview(holder);
            setPreviewRunning(true);
        }
        catch (final IOException e)
        {
            L.e("Failed to show preview - unable to connect camera to preview (IOException)", e);
            mInterface.onCapturePreviewFailed();
        }
        catch (final RuntimeException e)
        {
            L.e("Failed to show preview - unable to start camera preview (RuntimeException)", e);
            mInterface.onCapturePreviewFailed();
        }
    }
    
    @Override
    public void surfaceDestroyed(final SurfaceHolder holder)
    {
    }
    
    public void releasePreviewResources()
    {
        if (mPreviewRunning)
        {
            try
            {
                mCameraWrapper.stopPreview();
                setPreviewRunning(false);
            }
            catch (final Exception e)
            {
                L.e("Failed to clean up preview resources", e);
            }
        }
    }
    
    protected void setPreviewRunning(boolean running)
    {
        mPreviewRunning = running;
    }
}
