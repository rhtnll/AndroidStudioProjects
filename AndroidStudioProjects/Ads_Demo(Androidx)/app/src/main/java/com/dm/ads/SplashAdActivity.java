package com.dm.ads;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.dm.sdk.ads.splash.SplashAD;
import com.dm.sdk.ads.splash.SplashAdListener;
import com.dm.sdk.common.util.AdError;

public class SplashAdActivity extends Activity implements SplashAdListener {
    private SplashAD splashAD;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ViewGroup contentView;
    private static final String TAG = SplashAdActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hideSystemNavigationBar();
        contentView = this.findViewById(R.id.splash_container);
        splashAD = new SplashAD(this, "96AgXfhA0XHyooOBVl", "A1300291007", this, 10000);
        //如果不使用预加载的话，可以直接使用fetchAndShowIn()方法拉取并直接加载显示开屏广告
        splashAD.fetchAndShowIn(contentView);
    }

    private void hideSystemNavigationBar() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View view = this.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onAdDismissed() {
        Log.d(TAG,"页面消失了");
        this.finish();
    }

    @Override
    public void onNoAd(AdError error) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    @Override
    public void onAdPresent() {
        Log.d(TAG,"展现了");
    }

    @Override
    public void onAdClicked() {
        Log.d(TAG,"点击了页面内容");
    }

    @Override
    public void onAdFilled() {
        Log.d(TAG,"下发广告了");
    }


    @Override
    public void onAdLoaded(long expireTimestamp) {
        //expireTimestamp代表广告的过期时间，如果开发者采用加载显示分离的方法加载广告的话，需要再次方法判断广告是否过期
        Log.d(TAG,"广告加载完成，可以展示了");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
