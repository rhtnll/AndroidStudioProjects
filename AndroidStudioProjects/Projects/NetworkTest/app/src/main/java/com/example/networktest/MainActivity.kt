package com.example.networktest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendRequestBtn.setOnClickListener {
//            sendRequestWithHttpURLConnection()
            sendRequestWithOkHttp()
        }
    }

    private fun sendRequestWithHttpURLConnection() {
        // 开启线程来发起网络请求
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val rurl="http://floor.huluxia.com/post/detail/ANDROID/2.3?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=6D97B057C686648A57A7C6FD632AAFE7B5470BA61633958DF391F19B17135BBB2EF819FF52D0B50F89DE376A3FD84A0FF4254154817322F9&device_code=%5Bw%5D02%3A00%3A00%3A00%3A00%3A00%5Bd%5D9a1874be-e845-4a13-9483-c8dabda034d8&post_id=44093263&page_no=1&page_size=20&doc=1"
                val url = URL(rurl)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
                // 下面对获取到的输入流进行读取
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val url="http://floor.huluxia.com/post/detail/ANDROID/2.3?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=6D97B057C686648A57A7C6FD632AAFE7B5470BA61633958DF391F19B17135BBB2EF819FF52D0B50F89DE376A3FD84A0FF4254154817322F9&device_code=%5Bw%5D02%3A00%3A00%3A00%3A00%3A00%5Bd%5D9a1874be-e845-4a13-9483-c8dabda034d8&post_id=44093263&page_no=1&page_size=20&doc=1"
                val client = OkHttpClient()
                val request = Request.Builder()
                    // 指定访问的服务器地址是电脑本机
//                    .url("http://10.0.2.2/get_data.xml")
//                  .url("http://10.0.2.2/get_data.json")
                    .url(url)
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
//                    parseXMLWithPull(responseData)
//                    parseXMLWithSAX(responseData)
                    parseJSONWithJSONString(responseData)
//                    parseJSONWithGSON(responseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showResponse(response: String) {
        runOnUiThread {
            // 在这里进行UI操作，将结果显示到界面上
            responseText.text = response
        }
    }

    private fun parseXMLWithPull(xmlData: String) {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlData))
            var eventType = xmlPullParser.eventType
            var id = ""
            var name = ""
            var version = ""
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name
                when (eventType) {
                    // 开始解析某个节点
                    XmlPullParser.START_TAG -> {
                        when (nodeName) {
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                    }
                    // 完成解析某个节点
                    XmlPullParser.END_TAG -> {
                        if ("app" == nodeName) {
                            Log.d("MainActivity", "id is $id")
                            Log.d("MainActivity", "name is $name")
                            Log.d("MainActivity", "version is $version")
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseXMLWithSAX(xmlData: String) {
        try {
            val factory = SAXParserFactory.newInstance()
            val xmlReader = factory.newSAXParser().getXMLReader()
            val handler = ContentHandler()
            // 将ContentHandler的实例设置到XMLReader中
            xmlReader.contentHandler = handler
            // 开始执行解析
            xmlReader.parse(InputSource(StringReader(xmlData)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseJSONWithJSONObject(jsonData: String) {
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("post")
                val name = jsonObject.getString("comments")
                val version = jsonObject.getString("msg")
                Log.d("MainActivity", "id is $id")
                Log.d("MainActivity", "name is $name")
                Log.d("MainActivity", "version is $version")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseJSONWithJSONString(jsonData: String) {
        try {
            val jsonObject = JSONObject(jsonData)
            val post = jsonObject.getString("post")
            val jsonObjectpost = JSONObject(post)
            val title = jsonObjectpost.getString("title")
            val detail = jsonObjectpost.getString("detail")
            val images = jsonObjectpost.getString("images")
            showResponse(images)
            //Log.d("MainActivity", post)
            //val comments = jsonObject.getString("comments")
            val jsonArray = JSONArray(images)
            //Log.d("MainActivity", comments)
            for (i in 0 until jsonArray.length()) {
                val pictureurl = jsonArray.getString(i)
                val url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg"
                //val jsonObject2 = jsonArray.getJSONObject(i)
                //val pictureurl = jsonObject2.get()
                //val name = jsonObject.get("comments")
                //val text = jsonObject.getString("images")
                Log.d("MainActivity", "pictureurl is $pictureurl")
                Glide.with(this).load(url).into(imageView);
                //Log.d("MainActivity", jsonObject2.toString())
                //Log.d("MainActivity", "version is $version")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseJSONWithGSON(jsonData: String) {
        val gson = Gson()
        val typeOf = object : TypeToken<List<App>>() {}.type
        val appList = gson.fromJson<List<App>>(jsonData, typeOf)
        for (app in appList) {
            Log.d("MainActivity", "id is ${app.images}")
            Log.d("MainActivity", "name is ${app.user}")
            Log.d("MainActivity", "version is ${app.text}")
        }
    }

}
