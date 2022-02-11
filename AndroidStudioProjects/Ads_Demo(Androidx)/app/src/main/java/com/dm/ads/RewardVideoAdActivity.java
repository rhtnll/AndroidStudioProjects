package com.dm.ads;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dm.sdk.ads.rewardvideo.RewardVideoAd;
import com.dm.sdk.ads.rewardvideo.RewardVideoAdListener;
import com.dm.sdk.common.util.AdError;

public class RewardVideoAdActivity extends BaseActivity implements View.OnClickListener, RewardVideoAdListener {

    private Button btRewardVideoPreload;
    private Button btRewardVideoPlay;
    private Button btPpids;
    private RewardVideoAd rewardVideoAd;
    private static final String TAG = PreloadVideoActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video_ad);
        btRewardVideoPreload = findViewById(R.id.bt_reward_video_preload);
        btRewardVideoPlay = findViewById(R.id.bt_reward_video_play);
        btPpids = findViewById(R.id.bt_ppids);
        btRewardVideoPreload.setOnClickListener(this);
        btRewardVideoPlay.setOnClickListener(this);
        btPpids.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reward_video_preload:
                //普通预缓存
                Intent preloadIntent = new Intent(this, PreloadVideoActivity.class);
                startActivity(preloadIntent);
                break;
            case R.id.bt_reward_video_play:
                //边下边播
                rewardVideoAd = new RewardVideoAd(this, "96AgXfhA0XHyooOBVl",
                        "A0900291322", this);
                rewardVideoAd.showAd();
                break;
            case R.id.bt_ppids:
                //多代码位预缓存
                Intent playingWithLoadIntent = new Intent(this, PlayWhileDownloadingActivity.class);
                startActivity(playingWithLoadIntent);
                break;
        }
    }

    @Override
    public void onVideoCached() {
        //这里使用了边下边播的功能，因此此方法不会回调
    }

    @Override
    public void onADShow() {
        Log.d(TAG, "onADShow");
    }

    @Override
    public void onReward() {
        Log.d(TAG, "onReward");
    }

    @Override
    public void onADClick() {
        Log.d(TAG, "onADClick");
    }

    @Override
    public void onVideoComplete() {
        Log.d(TAG, "onVideoComplete");
    }

    @Override
    public void onADClose() {
        Log.d(TAG, "onADClose");
    }

    @Override
    public void onError(AdError error) {
        Log.d(TAG, "errorMsg : " + error.getErrorMsg());
    }
}
