package com.dm.ads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PrepareShowSplashAdActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fetchAndShowBtn;
    private Button fetchAdOnlyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_show_splash_ad);
        fetchAndShowBtn = findViewById(R.id.fetch_and_show_btn);
        fetchAdOnlyBtn = findViewById(R.id.fetch_ad_only_btn);
        fetchAndShowBtn.setOnClickListener(this);
        fetchAdOnlyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fetch_and_show_btn:
                //拉取并显示开屏广告
                Intent fetchAndShowIntent = new Intent(this, SplashAdActivity.class);
                startActivity(fetchAndShowIntent);
                break;
            case R.id.fetch_ad_only_btn:
                //拉取与显示开屏广告分离
                Intent departFetchAndShowIntent = new Intent(this,DepartFetchAndShowActivity.class);
                startActivity(departFetchAndShowIntent);
                break;
        }
    }
}
