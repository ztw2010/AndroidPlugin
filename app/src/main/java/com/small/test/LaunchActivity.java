package com.small.test;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import net.wequick.small.Small;

/**
 * An example launcher activity that setUp the Small bundles and launch the main plugin.
 */
public class LaunchActivity extends Activity {

    private static final long MIN_INTRO_DISPLAY_TIME = 3000000000l; // mμs -> 1.0s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = LaunchActivity.this.getSharedPreferences("profile", 0);
        final SharedPreferences.Editor se = sp.edit();
        final long tStart = System.nanoTime();
        se.putLong("setUpStart", tStart);
        Small.setUp(LaunchActivity.this, new net.wequick.small.Small.OnCompleteListener() {
            @Override
            public void onComplete() {
                long tEnd = System.nanoTime();
                se.putLong("setUpFinish", tEnd).apply();
                long offset = tEnd - tStart;
                if (offset < MIN_INTRO_DISPLAY_TIME) {
                    // 这个延迟仅为了让 Logo 显示足够的时间
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Small.openUri("main", LaunchActivity.this);
                            finish();
                        }
                    }, (MIN_INTRO_DISPLAY_TIME - offset) / 1000000);
                } else {
                    Small.openUri("main", LaunchActivity.this);
                    finish();
                }
            }
        });
    }
}
