package com.small.test.app.bdmap.baidumapsdk.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.small.test.app.bdmap.R;


public class ShareDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_demo);
    }

    public void startShareDemo(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ShareDemoActivity.class);
        startActivity(intent);

    }

}
