package com.dm.ads;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dm.sdk.ads.rewardvideo.RewardVideoAd;
import com.dm.sdk.ads.rewardvideo.RewardVideoAdListener;
import com.dm.sdk.common.util.AdError;

/**
 * @author: yongqiwang
 * @description: 预缓存支持多代码位示例
 * @date: on 2020-07-17 17:30
 **/
public class PlayWhileDownloadingActivity extends BaseActivity implements View.OnClickListener, RewardVideoAdListener {

    private RewardVideoAd rewardVideoAd1;
    private RewardVideoAd rewardVideoAd2;
    private RewardVideoAd rewardVideoAd3;
    private Button btPreload1;
    private Button btPreload2;
    private Button btPreload3;
    private Button btRewardVideoPlaying1;
    private Button btRewardVideoPlaying2;
    private Button btRewardVideoPlaying3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_while_downloading);
        rewardVideoAd1 = new RewardVideoAd(this, "96AgXfhA0XHyooOBVl",
                "A0900291461", this, true);
        rewardVideoAd2 = new RewardVideoAd(this, "96AgXfhA0XHyooOBVl",
                "A1000291462", this, true);
        rewardVideoAd3 = new RewardVideoAd(this, "96AgXfhA0XHyooOBVl",
                "A0900291322", this, true);
        btPreload1 = findViewById(R.id.bt_preload1);
        btPreload2 = findViewById(R.id.bt_preload2);
        btPreload3 = findViewById(R.id.bt_preload3);
        btRewardVideoPlaying1 = findViewById(R.id.bt_reward_video_playing1);
        btRewardVideoPlaying2 = findViewById(R.id.bt_reward_video_playing2);
        btRewardVideoPlaying3 = findViewById(R.id.bt_reward_video_playing3);
        btPreload1.setOnClickListener(this);
        btPreload2.setOnClickListener(this);
        btPreload3.setOnClickListener(this);
        btRewardVideoPlaying1.setOnClickListener(this);
        btRewardVideoPlaying2.setOnClickListener(this);
        btRewardVideoPlaying3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_preload1:
                rewardVideoAd1.loadAD();
                break;
            case R.id.bt_preload2:
                rewardVideoAd2.loadAD();
                break;
            case R.id.bt_preload3:
                rewardVideoAd3.loadAD();
                break;
            case R.id.bt_reward_video_playing1:
                //播放视频
                if (rewardVideoAd1.hasAD()) {
                    rewardVideoAd1.showAd();
                } else {
                    Toast.makeText(this, "成功预加载广告之后才能播放", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_reward_video_playing2:
                //播放视频
                if (rewardVideoAd2.hasAD()) {
                    rewardVideoAd2.showAd();
                } else {
                    Toast.makeText(this, "成功预加载广告之后才能播放", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_reward_video_playing3:
                //播放视频
                if (rewardVideoAd3.hasAD()) {
                    rewardVideoAd3.showAd();
                } else {
                    Toast.makeText(this, "成功预加载广告之后才能播放", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onVideoCached() {

    }

    @Override
    public void onADShow() {

    }

    @Override
    public void onReward() {

    }

    @Override
    public void onADClick() {

    }

    @Override
    public void onVideoComplete() {

    }

    @Override
    public void onADClose() {

    }

    @Override
    public void onError(AdError error) {

    }
}
