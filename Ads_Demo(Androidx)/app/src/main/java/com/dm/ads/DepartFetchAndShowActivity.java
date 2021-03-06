package com.dm.ads;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dm.sdk.ads.splash.SplashAD;
import com.dm.sdk.ads.splash.SplashAdListener;
import com.dm.sdk.common.util.AdError;

import util.TimeUtil;

public class DepartFetchAndShowActivity extends Activity implements View.OnClickListener, SplashAdListener {

    private ViewGroup contentView;
    private LinearLayout leaPrepare;
    private Button prepareAdBtn;
    private Button showAdBtn;
    private TextView tvTimeTip;
    private RelativeLayout relaShowAd;
    private SplashAD splashAD;
    private boolean hasAd = false;
    private boolean isExpire = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemNavigationBar();
        setContentView(R.layout.activity_depart_fetch_and_show);
        splashAD = new SplashAD(this, "96AgXfhA0XHyooOBVl", "A1300291007", this, 10000);
        contentView = findViewById(R.id.splash_container);
        leaPrepare = findViewById(R.id.lea_prepare);
        prepareAdBtn = findViewById(R.id.prepare_ad_btn);
        showAdBtn = findViewById(R.id.show_ad_btn);
        tvTimeTip = findViewById(R.id.tv_time_tip);
        relaShowAd = findViewById(R.id.rela_show_ad);
        showAdBtn.setOnClickListener(this);
        prepareAdBtn.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prepare_ad_btn:
                //?????????????????????
                splashAD.fetchAdOnly();
                break;
            case R.id.show_ad_btn:
                //?????????????????????
                if (hasAd) {
                    if (!isExpire) {
                        relaShowAd.setVisibility(View.VISIBLE);
                        leaPrepare.setVisibility(View.GONE);
                        splashAD.showAd(contentView);
                    } else {
                        tvTimeTip.setVisibility(View.VISIBLE);
                        tvTimeTip.setText("??????????????????????????????");
                    }
                } else {
                    tvTimeTip.setVisibility(View.VISIBLE);
                    tvTimeTip.setText("???????????????????????????????????????");
                }
                break;
        }
    }

    @Override
    public void onAdDismissed() {
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

    }

    @Override
    public void onAdClicked() {

    }

    @Override
    public void onAdFilled() {

    }

    @Override
    public void onAdLoaded(long expireTimestamp) {
        if (expireTimestamp > System.currentTimeMillis()) {
            hasAd = true;
            isExpire = false;
            //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            tvTimeTip.setVisibility(View.VISIBLE);
            tvTimeTip.setText("???????????????????????????" + TimeUtil.timeParse(expireTimestamp - System.currentTimeMillis())
                    + "??????????????????????????????????????????????????????showAd()??????");
        } else {
            isExpire = true;
            hasAd = false;
        }

    }
}
