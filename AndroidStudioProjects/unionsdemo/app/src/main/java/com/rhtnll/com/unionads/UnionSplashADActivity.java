package com.rhtnll.com.unionads;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.domob.sdk.common.util.AdError;
import com.domob.sdk.unionads.splash.UnionSplashAD;
import com.domob.sdk.unionads.splash.UnionSplashAdListener;
import com.rhtnll.com.MainActivity;
import com.rhtnll.com.R;


public class UnionSplashADActivity extends Activity implements UnionSplashAdListener {
    private UnionSplashAD splashAD;
    private boolean canJump = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        requestPermissions();
        hideSystemNavigationBar();

        FrameLayout contentView = this.findViewById(R.id.splash_container);

        splashAD = new UnionSplashAD(this, "96AgX5nQ0XMrIoOBil", "A1209891885", this, 5000);
        splashAD.fetchAndShowIn(contentView);
    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 10000);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 没有权限，申请权限。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},
                    10001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //申请广告权限回调
        if (requestCode == 10000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //requestPermissions();
            } else {
                Toast.makeText(getApplicationContext(), "You denied the READ_PHONE_STATE permission", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 10001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //requestPermissions();
            } else {
                Toast.makeText(getApplicationContext(), "You denied the ACCESS_FINE_LOCATION permission", Toast.LENGTH_SHORT).show();
            }
        }
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
        Log.d("unions_ads", "onAdDismissed");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onNoAd(AdError error) {
        final String msg = error.getErrorMsg();
        final int code = error.getErrorCode();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("result", "code : " + code + "  msg : " + msg);
                setResult(RESULT_OK, intent);
                finish();
            }
        }, 3000);
        Log.d("unions_ads", "code : " + error.getErrorCode() + "  msg : " + error.getErrorMsg());
    }

    @Override
    public void onAdPresent() {
        Log.d("unions_ads", "广告展现了");
    }

    @Override
    public void onAdClicked() {
        this.finish();
        Log.d("unions_ads", "点击了页面内容");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("unions_ads", "onResume");
        //此步骤主要是处理点击广告之后，退出当前的开屏页面，开发者可以根据自己的需求自己处理，不一定按照此方式处理
        if (canJump) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("unions_ads", "onPause");
        canJump = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("unions_ads", "onDestroy");
        //清空Handler队列中的所有消息，如果开发者在onNoAd()方法中没有使用Handler方式处理，可以不用在此处添加这行代码
        handler.removeCallbacksAndMessages(null);
    }
}
