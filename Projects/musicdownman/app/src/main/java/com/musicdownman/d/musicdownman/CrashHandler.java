package com.musicdownman.d.musicdownman;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * 作用:
 * 1.收集错误信息
 * 2.保存错误信息
 * Created by shenhua on 2017-08-22-0022.
 * Email shenhuanet@126.com
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler sInstance = null;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    // 保存手机信息和异常信息
    private Map<String, String> mMessage = new HashMap<>();

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    synchronized (CrashHandler.class) {
                        sInstance = new CrashHandler();
                    }
                }
            }
        }
        return sInstance;
    }

    private CrashHandler() {
    }

    /**
     * 初始化默认异常捕获
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        // 获取默认异常处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将此类设为默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }



    /**
     * 是否人为捕获异常
     *
     * @param e Throwable
     * @return true:已处理 false:未处理
     */
    private boolean handleException(Throwable e) {
        if (e == null) {// 异常是否为空
            return false;
        }
        new Thread() {// 在主线程中弹出提示
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "捕获到异常", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        return false;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

    }
}