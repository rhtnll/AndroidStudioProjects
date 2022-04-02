package com.musicdownman.d.musicdownman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zzy on 2018/2/8.
 */

public class Qmusic {
    static int isgetData = 0;
    static String songdata = "";
    static boolean forff = true;

    public static ArrayList SearchMusic(String SongName) {
        try {
            SongName = URLEncoder.encode(SongName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://c.y.qq.com/soso/fcgi-bin/client_search_cp?ct=24&qqmusic_ver=1298&new_json=1&remoteplace=txt.yqq.center&t=0&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=1&n=50&w=" + SongName + "&jsonpCallback=searchCallbacksong2020&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
        Log.d("调试输出", url);
        OkHttpClient http = new OkHttpClient();
//创建一个Request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
//通过client发起请求
        http.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    songdata = str.substring(23, str.length() - 1);
                    //Log.d("调试输出", str);
                    //Log.d("调试输出", songdata);
                    isgetData = 1;
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                forff = false;
            }

        });
        while (forff) {
            if (isgetData == 1) {

                try {
                    ArrayList list = new ArrayList();
                    JSONObject json = new JSONObject(songdata);
                    JSONObject json_data = json.getJSONObject("data");
                    JSONObject json_song = json_data.getJSONObject("song");
                    JSONArray json_list = json_song.getJSONArray("list");
                   SongData SongInfoDataList = new SongData();

                    for (int i = 0; i < json_list.length(); i++) {

                        JSONObject json_listobj = json_list.getJSONObject(i);
                        JSONObject file = json_listobj.getJSONObject("file");
                        JSONObject album = json_listobj.getJSONObject("album");
                        SongInfoDataList = new SongData();
                        //JSONObject mv=json_listobj.getJSONObject("mv");
                        String singername = json_listobj.getJSONArray("singer").getJSONObject(0).getString("name");
                        SongInfoDataList.setSongname(json_listobj.getString("title"));
                        SongInfoDataList.setSingername(singername);
                        SongInfoDataList.setSize_128(file.getString("size_128"));
                        SongInfoDataList.setSize_320( file.getString("size_320"));
                        //maplist.put("size_aac", file.getString("size_aac"));
                        SongInfoDataList.setSize_ape(file.getString("size_ape"));
                        //maplist.put("size_dts", file.getString("size_dts"));
                        SongInfoDataList.setSize_flac(file.getString("size_flac"));
                        //maplist.put("size_ogg", file.getString("size_ogg"));
                        //maplist.put("size_try", file.getString("size_try"));
                        SongInfoDataList.setAlbum_mid(album.getString("mid"));
                        SongInfoDataList.setMedia_mid( file.getString("media_mid"));

                        // maplist.put("albumbase64", Base64.encodeToString(GetAlbumImg(maplist.get("album_mid")));
                        list.add(SongInfoDataList);
                        Log.d("调试输出",SongInfoDataList.getAlbum_mid());
                        //Log.d("调试输出", "aaaaa");*/
                    }

                    return list;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                Log.d("调试输出", "等待中");
            }
        }
        return null;
    }

    public static String GetDownUrl(int fileType, String StrMediaMid, String vkey, String guid) {
        String url = "";
        String urlhttp = "";

        switch (fileType) {
            case 1: {
                // Map<String, String> maplist = new HashMap<>();
                // maplist = (Map<String, String>) list.get(listid);
                Log.d("点击事件", "flacfile");
                //Log.d("点击事件", "onItemClick: " + maplist.get("title") + maplist.get("media_mid") + maplist.get("size_ape"));
                url = "http://dl.stream.qqmusic.qq.com/F000" + StrMediaMid + ".flac?vkey=" + vkey + "&guid=" + guid + "&uin=1008611&fromtag=64";
            }
            break;
            case 2: {
                Log.d("点击事件", "apefile");
                url = "http://dl.stream.qqmusic.qq.com/A000" + StrMediaMid + ".ape?vkey=" + vkey + "&guid=" + guid + "&uin=1008611&fromtag=64";
            }
            break;
            case 3: {
                Log.d("点击事件", "320Kmp3");
                url = "http://dl.stream.qqmusic.qq.com/M800" + StrMediaMid + ".mp3?vkey=" + vkey + "&guid=" + guid + "&uin=1008611&fromtag=64";
            }
            break;
            case 4: {
                Log.d("点击事件", "128Kmp3");
                url = "http://dl.stream.qqmusic.qq.com/M500" + StrMediaMid + ".mp3?vkey=" + vkey + "&guid=" + guid + "&uin=1008611&fromtag=64";
                Log.d("调试输出", "GetDownUrl: " + url);
            }
            break;
        }
        return url;
    }

    public static void getalbummidim(String albummid, final ImageView imageView) {
        final String url = "http://y.gtimg.cn/music/photo_new/T002R90x90M000" + albummid + ".jpg?max_age=2592000";
        Log.d("调试输出", url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(getInternetPicture(url));
            }

        }
        ).start();

    }

    public static Bitmap getInternetPicture(String UrlPath) {
        Bitmap bm = null;
        // 1、确定网址
        // http://pic39.nipic.com/20140226/18071023_164300608000_2.jpg
        String urlpath = UrlPath;
        // 2、获取Uri
        try {
            URL uri = new URL(urlpath);

            // 3、获取连接对象、此时还没有建立连接
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            // 4、初始化连接对象
            // 设置请求的方法，注意大写
            connection.setRequestMethod("GET");
            // 读取超时
            connection.setReadTimeout(5000);
            // 设置连接超时
            connection.setConnectTimeout(5000);
            // 5、建立连接
            connection.connect();

            // 6、获取成功判断,获取响应码
            if (connection.getResponseCode() == 200) {
                // 7、拿到服务器返回的流，客户端请求的数据，就保存在流当中
                InputStream is = connection.getInputStream();
                // 8、从流中读取数据，构造一个图片对象GoogleAPI
                bm = BitmapFactory.decodeStream(is);
                // 9、把图片设置到UI主线程
                // ImageView中,获取网络资源是耗时操作需放在子线程中进行,通过创建消息发送消息给主线程刷新控件；

               // Log.i("", "网络请求成功");

            } else {
                //Log.v("tag", "网络请求失败");
                bm = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;

    }

    public void Calculationvkey() {

    }


}


