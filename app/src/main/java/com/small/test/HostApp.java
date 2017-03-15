package com.small.test;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.small.test.appstub.systeminit.SystemInit;

import net.wequick.small.Small;
import net.wequick.small.webkit.WebView;
import net.wequick.small.webkit.WebViewClient;

public class HostApp extends Application {

    public HostApp() {
        // This should be the very first of the application lifecycle.
        // It's also ahead of the installing of content providers by what we can avoid
        // the ClassNotFound exception on if the provider is unimplemented in the host.
        Small.preSetUp(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Optional
        Small.setBaseUri("http://www.tima.carnet/app");
        Small.setWebViewClient(new MyWebViewClient());
        SystemInit.getInstance().init(this.getApplicationContext());
    }

    private static final class MyWebViewClient extends WebViewClient {

        private ProgressDialog mDlg;

        @Override
        public void onPageStarted(Context context, WebView view, String url, Bitmap favicon) {
            mDlg = new ProgressDialog(context);
            mDlg.setMessage("Loading...");
            mDlg.show();
            super.onPageStarted(context, view, url, favicon);
        }

        @Override
        public void onPageFinished(Context context, WebView view, String url) {
            super.onPageFinished(context, view, url);
            mDlg.dismiss();
        }

        @Override
        public void onReceivedError(Context context, WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(context, view, errorCode, description, failingUrl);
            mDlg.dismiss();
            Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
        }
    }
}