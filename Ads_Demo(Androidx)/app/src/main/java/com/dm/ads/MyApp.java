package com.dm.ads;

import android.app.Application;

import com.bun.miitmdid.core.JLibrary;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化移动安全联盟MSA
        JLibrary.InitEntry(getBaseContext());
    }
}
