package com.dm.ads;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dm.sdk.ads.interstitial.InterstitialAd;
import com.dm.sdk.ads.interstitial.InterstitialAdListener;
import com.dm.sdk.common.util.AdError;

public class InterstitialAdActivity extends BaseActivity implements InterstitialAdListener, View.OnClickListener {

    private Button btReloadAd;
    private Button btShowAd;
    private InterstitialAd interstitialAd;
    private boolean isAdLoaded = false;
    private static final String TAG = InterstitialAdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);
        btReloadAd = findViewById(R.id.bt_reload_ad);
        btShowAd = findViewById(R.id.bt_show_ad);
        //不同于传统的插屏广告，banner广告参数中的"+350"代表了用户如果点击广告
        //成功激活唤醒或者下载任务会获得的对应奖励，这里的+350只是列举，具体的激励内容需
        //要根据开发者具体的业务逻辑去定义,如果此参数不传则显示的是传统插屏广告
        interstitialAd = new InterstitialAd(this, "96AgXfhA0XHyooOBVl", "A1400291114",
                this,"+350");
        btReloadAd.setOnClickListener(this);
        btShowAd.setOnClickListener(this);
    }

    @Override
    public void onNoAd(AdError error) {
        Log.d(TAG,"出错了 , 错误码 :" + error.getErrorCode() + ",错误信息: " + error.getErrorMsg());
    }

    @Override
    public void onAdClicked() {
        Log.d(TAG,"广告被点击了");
    }

    @Override
    public void onAdReceive() {
        Log.d(TAG,"广告加载完成");
        Toast.makeText(this, "插屏广告加载成功，可以开始展示了", Toast.LENGTH_SHORT).show();
        isAdLoaded = true;
    }

    @Override
    public void onAdPresent() {
        Log.d(TAG,"广告展现了");
    }

    @Override
    public void onReward() {
        Log.d(TAG,"广告激励任务完成");
    }

    @Override
    public void onAdClosed() {
        Log.d(TAG,"广告关闭了");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reload_ad:
                interstitialAd.loadAd();
                break;
            case R.id.bt_show_ad:
                if (isAdLoaded) {
                    interstitialAd.showAd();
                    isAdLoaded = false;
                } else {
                    Toast.makeText(this, "请加载广告后再进行展示", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
