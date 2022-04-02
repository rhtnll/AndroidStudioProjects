package com.example.videoplayer;

import android.content.Context;

public class PixelUtil {

    private static Context mcontext;

    public static void initContext(Context context) {
        mcontext = context;
    }

    public static int Dp2Px(float dp) {
        final float scale = mcontext.getResources().getDisplayMetrics().density; //当前屏幕密度因子
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(float px) {
        final float scale = mcontext.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}


