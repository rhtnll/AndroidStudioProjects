package com.dm.ads;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dm.sdk.ads.rewardvideo.RewardVideoAd;
import com.dm.sdk.ads.rewardvideo.RewardVideoAdListener;
import com.dm.sdk.common.util.AdError;

public class PreloadVideoActivity extends BaseActivity implements View.OnClickListener, RewardVideoAdListener {

    private Button btRewardVideoPreload;
    private Button btRewardVideoPlay;
    private RewardVideoAd rewardVideoAd;
    private static final String TAG = PreloadVideoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload_video);
        btRewardVideoPreload = findViewById(R.id.bt_reward_video_preload);
        btRewardVideoPlay = findViewById(R.id.bt_reward_video_play);
        //初始化激励视频构造方法,这里选择了预缓存的方式
        rewardVideoAd = new RewardVideoAd(this, "96AgXfhA0XHyooOBVl",
                "A0900291322", this, true);
        btRewardVideoPreload.setOnClickListener(this);
        btRewardVideoPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reward_video_preload:
                //预加载
                rewardVideoAd.loadAD();
                break;
            case R.id.bt_reward_video_play:
                //播放视频
                if (rewardVideoAd.hasAD()) {
                    rewardVideoAd.showAd();
                } else {
                    Toast.makeText(this, "成功预加载广告之后才能播放", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onVideoCached() {
        Log.e(TAG,"onVideoCached");
        Toast.makeText(this, "视频广告加载完成可以开始播放", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onADShow() {
        Log.e(TAG,"onADShow");
    }

    @Override
    public void onReward() {
        Log.e(TAG,"onReward");
    }

    @Override
    public void onADClick() {
        Log.e(TAG,"onADClick");
    }

    @Override
    public void onVideoComplete() {
        Log.e(TAG,"onVideoComplete");
    }

    @Override
    public void onADClose() {
        Log.e(TAG,"onADClose");
    }

    @Override
    public void onError(AdError error) {
        Log.e(TAG,"errorMsg : " + error.getErrorMsg());
    }
}
