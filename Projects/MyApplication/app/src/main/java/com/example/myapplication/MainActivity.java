package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.util.ImageUtils;
import com.example.myapplication.util.UploadUtil;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CHOOSE_IMAGE = 0x01;

    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT = 0xff;

    private TextView photoPath;
    private TextView photo_url;
    private ImageView photo;

    ProgressDialog progressDialog;

    public static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mainActivity;

        public MyHandler(WeakReference<MainActivity> mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                // 使用response文本框显示服务器响应信息
                Log.d("lcj", msg.obj.toString());
                mainActivity.get().photo_url.setText(msg.obj.toString());
                mainActivity.get().showProgressDialog(false);
                Glide.with(mainActivity.get()).load(msg.obj).into(mainActivity.get().photo);
            }
        }
    }

    private Handler handler = new MyHandler(new WeakReference<>(this));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoPath = (TextView) findViewById(R.id.photo_path);
        photo_url = (TextView) findViewById(R.id.photo_url);
        photo = (ImageView) findViewById(R.id.photo);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.upload_image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareToOpenAlbum();
            }
        });

        photo_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard();
            }
        });

    }

    private void copyToClipboard() {
        
        //获取剪贴板管理器
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("ImageUploader", photo_url.getText());
        // 将ClipData内容放到系统剪贴板里
        cm.setPrimaryClip(mClipData);

        Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
    }

    private void showProgressDialog(boolean flag) {

        progressDialog.setTitle("提示");
        //progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setMessage("正在上传");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if (flag) {
            progressDialog.show();
            //Toast.makeText(getApplicationContext(), "开始上传", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
        }

    }

    private void prepareToOpenAlbum() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(MainActivity.this, "You denied the write_external_storage permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d("lcj", "Uri = " + uri);
            String path = ImageUtils.getRealPathFromUri(this, uri);
            Log.d("lcj", "realPath = " + path);
            //Glide.with(this).load("http://img.lieyou888.com/000/2020/08/21/633237b3-5754-45a1-b632-30c9a539ba9f.png").into(photo);
            showProgressDialog(true);
            upload(path);
            photoPath.setVisibility(View.VISIBLE);
            photoPath.setText(path);
            int requiredHeight = photo.getHeight();
            int requiredWidth = photo.getWidth();
            //Bitmap bm = ImageUtils.decodeSampledBitmapFromDisk(path, requiredWidth, requiredHeight);
            //photo.setImageBitmap(bm);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void upload(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = UploadUtil.uploadImage(getResources().getString(R.string.api), path);
                Log.d("lcj", url);
                Message msg = new Message();
                msg.what = 0x123;
                msg.obj = url;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 菜单的监听方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fist:
                SharedPreferences getInfo = getSharedPreferences("info", MODE_PRIVATE);
                int total = getInfo.getInt("total",5);
                int used = getInfo.getInt("used",5);
                int available = getInfo.getInt("available",5);
                new AlertDialog.Builder(this)
                        .setTitle("关于")
                        .setMessage("总上传次数：" + String.valueOf(total) + "\n已上传次数：" + String.valueOf(used) +"\n剩余上传次数：" + String.valueOf(available))
                        .setPositiveButton("确定", null)
                        .show();
            case R.id.second:
                SharedPreferences putInfo = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = putInfo.edit();
                editor.putInt("total",5);
                editor.putInt("used",4);
                editor.putInt("available",1);
                editor.commit();
                break;
            default:
                break;
        }
        return true;
    }


}
