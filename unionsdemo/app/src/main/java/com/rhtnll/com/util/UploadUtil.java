package com.rhtnll.com.util;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadUtil {

    private final String url;
    private final String imagePath;

    public UploadUtil(String url, String imagePath)
    {
        this.url = url;
        this.imagePath = imagePath;
    }

    public static String uploadImage(String url, String imagePath) {
        try {
            File file = new File(imagePath);
            String fileName = file.getName();
            //Log.d("unions_ads", fileName);
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName,
                            RequestBody.create(MediaType.parse("multipart/form-data"), file))
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            //Log.d("unions_ads", response.body().string());
            JSONObject jsonObject = new JSONObject(response.body().string());
            //JSONObject jsonObject = new JSONObject(response.body().string());
            //return jsonObject.optString("status");
            if (jsonObject.optInt("status") == 1){

                return jsonObject.optString("url");
            }
            return response.body().string();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

