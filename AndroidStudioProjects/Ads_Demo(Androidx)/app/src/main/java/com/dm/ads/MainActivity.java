package com.dm.ads;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button splash_button = findViewById(R.id.splash_button);
        Button information_flow_button = findViewById(R.id.information_flow_button);
        Button table_screen_button = findViewById(R.id.table_screen_button);
        Button banner_button = findViewById(R.id.banner_button);
        Button ver_video_button = findViewById(R.id.ver_video_button);
        splash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrepareShowSplashAdActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
//        information_flow_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, InformationFlowADActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//        });
        table_screen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterstitialAdActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        banner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BannerAdActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        ver_video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RewardVideoAdActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 10000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                10001);
                    }
                } else {
//                    Toast.makeText(this, "权限被禁用,广告无法正常展示", Toast.LENGTH_LONG).show();
                }
                break;
            case 10001:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {// 没有权限，申请权限。
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},
                                10002);
                    }
                } else {
//                    Toast.makeText(this, "权限被禁用,广告无法正常展示", Toast.LENGTH_LONG).show();
                }
                break;
            case 10002:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
