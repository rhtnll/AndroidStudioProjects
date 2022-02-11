package com.example.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private LinearLayout controllerLayout;
    private ImageView play_controller_img, volumn_img, screen_img;
    private TextView current_text_tv, time_total_tv;
    private SeekBar play_seek, volume_seek;
    private static final int UPDATEUI = 1;
    private int screen_width, screen_heigh;
    private RelativeLayout videoLayout;
    private AudioManager mAudioManager;
    private boolean isFullScreen = false;
    private boolean isAdjust = false;
    private int threshold = 54;
    private float mBrightness;
    private ImageView operation_bg, operation_percent;
    // 定义手势检测器变量
    private GestureDetector detector;

    float lastX = 0;
    float lastY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        initUI();
        GestureDetector();
        setPlayerEvent();
    }

    protected void GestureDetector() {
        // 创建手势检测器
        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
                // 如果第一个触点事件的X坐标大于第二个触点事件的X坐标超过flipDistance
                // 也就是手势从右向左滑
                Log.d("MainActivity", "event1.getY()" + String.valueOf(event1.getY()));
                Log.d("MainActivity", "event2.getY()" + String.valueOf(event2.getX()));
                if (event2.getY() - event1.getY() > threshold) {
                    //Log.d("MainActivity", String.valueOf(event1.getX()));
                    //Log.d("MainActivity", "event1.getY()"+String.valueOf(event1.getY()));
                    //Log.d("MainActivity", "event2.getY()"+String.valueOf(event2.getX()));
                    //Log.d("MainActivity", String.valueOf(event2.getY()));
                    //Log.d("MainActivity", "DOWN");
                    return true;
                }
                // 如果第二个触点事件的X坐标大于第一个触点事件的X坐标超过flipDistance
                // 也就是手势从左向右滑
                else if (event2.getY() - event1.getY() < threshold) {
                    //Log.d("MainActivity", "UP");
                    return true;
                }
                return false;
            }
        });
    }

    private void updateTextViewWithTimeFormat(TextView textView, int millisecond) {
        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String str = null;
        if (hh != 0) {
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(str);
    }

    private Handler UIhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATEUI) {
                int currentposition = videoView.getCurrentPosition();
                int totalduration = videoView.getDuration();
                updateTextViewWithTimeFormat(current_text_tv, currentposition);
                updateTextViewWithTimeFormat(time_total_tv, totalduration);
                play_seek.setMax(totalduration);
                play_seek.setProgress(currentposition);
                UIhandler.sendEmptyMessageDelayed(UPDATEUI, 500);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        UIhandler.removeMessages(UPDATEUI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setPlayerEvent() {

        play_controller_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    play_controller_img.setImageResource(R.drawable.start);
                    //暂停播放
                    videoView.pause();
                    UIhandler.removeMessages(UPDATEUI);
                } else {
                    play_controller_img.setImageResource(R.drawable.pause);
                    //开始播放
                    videoView.start();
                    UIhandler.sendEmptyMessage(UPDATEUI);
                }
            }
        });

        play_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTextViewWithTimeFormat(current_text_tv, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                UIhandler.removeMessages(UPDATEUI);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                videoView.seekTo(progress);
                UIhandler.sendEmptyMessage(UPDATEUI);
            }
        });

        volume_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        screen_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreen) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        lastX = event.getX();
                        lastY = event.getY();
                        //Log.d("MainActivity", "DOWN");
                        //Log.d("MainActivity", String.valueOf(event.getX()));
                        //Log.d("MainActivity", String.valueOf(event.getY()));
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        float detlaX = event.getX()-lastX;
                        float detlaY = event.getY()-lastY;
                        float absdetlaX = Math.abs(detlaX);
                        float absdetlaY = Math.abs(detlaY);
                        if (absdetlaX > threshold && absdetlaY > threshold){
                            if (absdetlaX < absdetlaY){
                                isAdjust = true;
                            }
                            else {
                                isAdjust = false;
                            }
                        }
                        else if (absdetlaX<threshold && absdetlaY>threshold){
                            isAdjust = true;
                        }
                        else if (absdetlaX>threshold && absdetlaY<threshold){
                            isAdjust = false;
                        }
                        //Log.d("MainActivity", String.valueOf(isAdjust));
                        //Log.d("MainActivity", String.valueOf(event.getX()));
                        //Log.d("MainActivity", String.valueOf(screen_width/2));
                        //Log.d("MainActivity", String.valueOf(absdetlaX));
                        //Log.d("MainActivity", String.valueOf(absdetlaY));
                        //Log.d("MainActivity", String.valueOf(absdetlaX));
                        //Log.d("MainActivity", String.valueOf(absdetlaY));
                        //Log.d("MainActivity", String.valueOf(threshold));
                            if (event.getX() < screen_width/2){
                                if (detlaY > 0){
                                    //降低亮度
                                    Log.d("MainActivity","降低亮度");
                                }
                                else {
                                    //升高亮度
                                    Log.d("MainActivity","升高亮度");
                                }
                                changeBrightness(-detlaY);
                            }
                            else {
                                if (detlaY > 5){
                                    //减小声音
                                    Log.d("MainActivity","减小声音");
                                    int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                    int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Math.min(Math.max(--current, 0), max), 0);
                                    volume_seek.setProgress(current);
                                }
                                else if(detlaY < 0) {
                                    //增大声音
                                    Log.d("MainActivity","增大声音");
                                    int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                    int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Math.min(Math.max(++current, 0), max),0);
                                    volume_seek.setProgress(current);
                                }
                                else {

                                }
                                //changeVolume(-detlaY);
                            }
                        lastX = event.getX();
                        lastY = event.getY();
                        //Log.d("MainActivity", "MOVE");
                        //Log.d("MainActivity", String.valueOf(lastX));
                        //Log.d("MainActivity", String.valueOf(lastY));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        //Log.d("MainActivity", "UP");
                        //Log.d("MainActivity", String.valueOf(event.getX()));
                        //Log.d("MainActivity", String.valueOf(event.getY()));
                        break;
                    }
                }
                return true;
            }
        });

        /*
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                float lastX = 0;
                float lastY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        lastX = x;
                        lastY = y;
                        Log.d("MainActivity", "1");
                        //Log.d("MainActivity", String.valueOf(lastY));
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        Log.d("MainActivity", "2");
                        Log.d("MainActivity", String.valueOf(lastX));
                        Log.d("MainActivity", String.valueOf(lastY));
                        float detlaX = x-lastX;
                        float detlaY = y-lastY;
                        float absdetlaX = Math.abs(detlaX);
                        float absdetlaY = Math.abs(detlaY);
                        if (absdetlaX > threshold && absdetlaY > threshold){
                            if (absdetlaX < absdetlaY){
                                isAdjust = true;
                            }
                            else {
                                isAdjust = false;
                            }
                        }
                        else if (absdetlaX<threshold && absdetlaY>threshold){
                            isAdjust = true;
                        }
                        else if (absdetlaX>threshold && absdetlaY<threshold){
                            isAdjust = false;
                        }
                        //Log.d("MainActivity", String.valueOf(x));
                        //Log.d("MainActivity", String.valueOf(lastX));
                        //Log.d("MainActivity", String.valueOf(absdetlaX));
                        //Log.d("MainActivity", String.valueOf(absdetlaY));
                        //Log.d("MainActivity", String.valueOf(threshold));
                        if (isAdjust){
                            if (x < screen_width/2){
                                if (detlaY > 0){
                                    //降低亮度
                                    Log.d("MainActivity","降低亮度");
                                }
                                else {
                                    //升高亮度
                                    Log.d("MainActivity","升高亮度");
                                }
                                changeBrightness(-detlaY);
                            }
                            else {
                                if (detlaY > 0){
                                    //减小声音
                                    Log.d("MainActivity","减小声音");
                                }
                                else {
                                    //增大声音
                                    Log.d("MainActivity","增大声音");
                                }
                                changeVolume(-detlaY);
                            }
                        }
                        lastX = x;
                        lastY = y;
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        break;
                    }
                }
                return true;
            }
        });
    }

         */
    }

    private void changeVolume(float detlaY) {
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int index = (int) (detlaY / screen_heigh * max);
        int volume = Math.min(Math.max(++current, 0),max);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        volume_seek.setProgress(volume);
        Log.d("MainActivity", String.valueOf(detlaY / screen_heigh));
        //Log.d("MainActivity", String.valueOf(detlaY));
        //Log.d("MainActivity", String.valueOf(current));
        //Log.d("MainActivity", String.valueOf(max));
        operation_bg.setImageResource(R.drawable.pause);
        ViewGroup.LayoutParams layoutParams = operation_percent.getLayoutParams();
        layoutParams.width = (int) (PixelUtil.Dp2Px(94) * (float) volume / max);
        operation_percent.setLayoutParams(layoutParams);
    }

    private void changeBrightness(float detlaY) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        mBrightness = attributes.screenBrightness;
        float index = detlaY / screen_heigh / 3;
        mBrightness += index;
        Log.d("MainActivity", String.valueOf(mBrightness));
        if (mBrightness > 1.0f) {
            mBrightness = 1.0f;
        }
        if (mBrightness < 0.01f) {
            mBrightness = 0.01f;
        }
        attributes.screenBrightness = mBrightness;
        getWindow().setAttributes(attributes);
    }

    private void initUI() {
        PixelUtil.initContext(this);
        videoView = (VideoView) findViewById(R.id.videoView);
        controllerLayout = (LinearLayout) findViewById(R.id.controllerLayout);
        play_controller_img = (ImageView) findViewById(R.id.play_controller_img);
        current_text_tv = (TextView) findViewById(R.id.current_text_tv);
        time_total_tv = (TextView) findViewById(R.id.time_total_tv);
        play_seek = (SeekBar) findViewById(R.id.play_seek);
        volume_seek = (SeekBar) findViewById(R.id.volume_seek);
        videoLayout = (RelativeLayout) findViewById(R.id.videoLayout);
        volumn_img = (ImageView) findViewById(R.id.volumn_img);
        screen_img = (ImageView) findViewById(R.id.screen_img);
        operation_bg = (ImageView) findViewById(R.id.operation_bg);
        operation_percent = (ImageView) findViewById(R.id.operation_percent);
        screen_width = getResources().getDisplayMetrics().widthPixels;
        screen_heigh = getResources().getDisplayMetrics().heightPixels;
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume_seek.setMax(streamMaxVolume);
        volume_seek.setProgress(streamVolume);

        Uri uri = Uri.parse("https://www.zhuticlub.com:65/20200819/NxKev6rD/index.m3u8");
        //videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        //videoView.start();
        videoView.requestFocus();
    }

    private void setVideoViewScale(int width, int heigh) {
        ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = heigh;
        videoView.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = videoLayout.getLayoutParams();
        layoutParams1.width = width;
        layoutParams1.height = heigh;
        videoLayout.setLayoutParams(layoutParams1);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            volumn_img.setVisibility(View.VISIBLE);
            volume_seek.setVisibility(View.VISIBLE);
            isFullScreen = true;
            screen_width = getResources().getDisplayMetrics().widthPixels;
            screen_heigh = getResources().getDisplayMetrics().heightPixels;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtil.Dp2Px(240));
            volumn_img.setVisibility(View.GONE);
            volume_seek.setVisibility(View.GONE);
            isFullScreen = false;
            screen_width = getResources().getDisplayMetrics().widthPixels;
            screen_heigh = getResources().getDisplayMetrics().heightPixels;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }
}