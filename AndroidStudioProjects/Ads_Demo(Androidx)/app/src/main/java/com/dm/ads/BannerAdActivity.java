package com.dm.ads;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dm.sdk.ads.banner.CommonBannerAdListener;
import com.dm.sdk.ads.banner.CommonBannerAdView;
import com.dm.sdk.common.util.AdError;

public class BannerAdActivity extends BaseActivity implements CommonBannerAdListener {

    private FrameLayout fraBannerview;
    private Button btBanner;
    private CommonBannerAdView bannerAdView;
    private static final String TAG = BannerAdActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        fraBannerview = findViewById(R.id.fra_bannerview);
        btBanner = findViewById(R.id.bt_banner);
        //不同于传统的banner广告，banner广告参数中的"+350"代表了用户如果点击广告
        //成功激活唤醒或者下载任务会获得的对应奖励，这里的+350只是列举，具体的激励内容需
        //要根据开发者具体的业务逻辑去定义,如果此参数不传则显示的是传统banner广告
        bannerAdView = new CommonBannerAdView(BannerAdActivity.this, "96AgXfhA0XHyooOBVl", "A1500291115",
                this, "+350");
        fraBannerview.addView(bannerAdView);
        btBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载并展示banner广告
                bannerAdView.loadAd();
            }
        });
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
    }

    @Override
    public void onAdPresent() {
        Log.d(TAG,"广告展现了");
    }

    @Override
    public void onReward() {
        Log.d(TAG,"广告激励任务完成");
    }
}
