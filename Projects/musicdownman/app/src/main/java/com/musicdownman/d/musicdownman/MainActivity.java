package com.musicdownman.d.musicdownman;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ProgressBar progressBar;
    private EditText editTextSong;
    private ArrayList list;
    private PopupWindow pop;
    private View popupwindow_menu;
    int ScreenWidth;
    int ScreenHeight;
    int listid;
    final String vkey = "6F136E1C87A22823675333D109791C8BCE699264BD5D046E0582AB70676FD363D4E76801AFBAB42DE569BB0545915440374AD05E961CBC5A";
    final String guid = "1234567890";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        {   ActivityCompat.requestPermissions(MainActivity.this, new String[]{android
                .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        //请求权限
        }
        CrashHandler.getInstance().init(this);
            ScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
            ScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextSong = (EditText) findViewById(R.id.editText_song);
        listView = (ListView) findViewById(R.id.List);

         Button btn=(Button)findViewById(R.id.button);
         int btnHeight=btn.getHeight();


            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height =ScreenHeight-btnHeight;
            //listView.setLayoutParams(params);
            listView.setOnItemClickListener(this);

        popupwindow_menu = getLayoutInflater().inflate(R.layout.downmenu, null, false);



        popupwindow_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (pop != null && pop.isShowing()) {
                    pop.dismiss();
                    pop = null;
                }
                return false;
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
       //权限提示
        switch (requestCode)
        {
            case 1:
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    //创建文件夹
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/muscidownman");
                        if (!file.exists()) {
                            if (!file.mkdirs()){
                                Toast.makeText(this, "创建文件夹失败，请赋予权限", Toast.LENGTH_SHORT).show();
                            };
                        }
                    }
                    break;
                }
        }}


    public String getTime() {
        long timeStampSec = System.currentTimeMillis() / 1000;
        String timestamp = String.format("%010d", timeStampSec);
        return timestamp;

    }

    public void ShowDownMenu() {
        //弹出下载列表界面
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        pop = new PopupWindow(popupwindow_menu, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        pop.setWidth(ScreenWidth);
        pop.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        Button btn_flac = (Button) popupwindow_menu.findViewById(R.id.btn_flac);
        Button btn_ape = (Button) popupwindow_menu.findViewById(R.id.btn_ape);
        Button btn_320mp3 = (Button) popupwindow_menu.findViewById(R.id.btn_320mp3);
        Button btn_128mp3 = (Button) popupwindow_menu.findViewById(R.id.btn_128mp3);
        btn_flac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownMusic(1);
            }
        });
        btn_ape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownMusic(2);
            }
        });
        btn_320mp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownMusic(3);
            }
        });
        btn_128mp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownMusic(4);
            }
        });
    }

    public void djson(View v) {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = Qmusic.SearchMusic(editTextSong.getText().toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowListView();
                        progressBar.setVisibility(View.INVISIBLE);//隐藏
                    }
                });
            }
        }).start();

    }


    public void ShowListView() {
        songAdapter adapter=new songAdapter(this,list);
        listView.setAdapter(adapter);
        Log.d("调试输出", String.valueOf(list.hashCode()));

        //adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listid = i;
        ShowDownMenu();

    }

    public void DownMusic(int FileType) {
        SongData maplist = new SongData();
        maplist = (SongData) list.get(listid);
        String downloadUrl="" ;
        String filename="";
        String FileTypeNmae="";
        switch (FileType) {
            case 1: {

                Log.d("点击事件", "flacfile");
                //Log.d("点击事件", "onItemClick: " + maplist.get("title") + maplist.get("media_mid") + maplist.get("size_ape"));
                downloadUrl=Qmusic.GetDownUrl(1,maplist.getMedia_mid(),vkey,guid);
                FileTypeNmae=".flac";
            }
            break;
            case 2: {
                Log.d("点击事件", "apefile");
                downloadUrl=Qmusic.GetDownUrl(2,maplist.getMedia_mid(),vkey,guid);
                FileTypeNmae=".ape";
            }
            break;
            case 3: {
                Log.d("点击事件", "320Kmp3");
                downloadUrl=Qmusic.GetDownUrl(3,maplist.getMedia_mid(),vkey,guid);
                FileTypeNmae=".mp3";
            }
            break;
            case 4: {
                downloadUrl=Qmusic.GetDownUrl(4,maplist.getMedia_mid(),vkey,guid);
                FileTypeNmae=".mp3";
            }
            break;
        }



        filename=maplist.getSongname()+" - "+maplist.getSingername();
        DownMusic(filename,downloadUrl,FileTypeNmae);
        Log.d("点击事件", downloadUrl);
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    public void DownMusic(String filename,String downloadUrl,String FileType) {


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(filename);
        request.setAllowedOverRoaming(false);
        request.setDescription(filename);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir("/muscidownman/",filename+FileType);
        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);



    }


}
