package com.example.networktest

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val tpurl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg"

        //sendRequestWithOkHttp()
        requestPermission(this)
        //sendRequestWithOkHttp2()
        readTxtFile("/storage/emulated/0/zuowen/", "qb.txt")?.let { it1 -> division(it1) }


        sendRequestBtn.setOnClickListener {

        }
    }

    private fun sendRequestWithHttpURLConnection() {
        // 开启线程来发起网络请求
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://www.baidu.com")
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
                //showResponse(response.toString())
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
                //val url="http://floor.huluxia.com/post/detail/ANDROID/2.3?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=6D97B057C686648A57A7C6FD632AAFE7B5470BA61633958DF391F19B17135BBB2EF819FF52D0B50F89DE376A3FD84A0FF4254154817322F9&device_code=%5Bw%5D02%3A00%3A00%3A00%3A00%3A00%5Bd%5D9a1874be-e845-4a13-9483-c8dabda034d8&post_id=44093263&page_no=1&page_size=20&doc=1"
                val url = "http://floor.huluxia.com/post/detail/ANDROID/2.3?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=52F81A98A7C4DAE08AB8E79AC7B550338B418565BDDB51D15949135AAB9487819A52769551DE4A4772EF2EDAF159EA6088F54296C2FD9B53&device_code=%5Bw%5D02%3A00%3A00%3A00%3A00%3A00%5Bd%5D9a1874be-e845-4a13-9483-c8dabda034d8&post_id=44447699&page_no=1&page_size=20&doc=1"
                val client = OkHttpClient()
                val request = Request.Builder()
                    // 指定访问的服务器地址是电脑本机
//                    .url("http://10.0.2.2/get_data.xml")
                    .url(url)
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
//                    parseXMLWithPull(responseData)
//                    parseXMLWithSAX(responseData)
//                    parseJSONWithJSONObject(responseData)
                    parseJSONWithJSONString(responseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(Exception::class)
    fun readTxtFile(filePath: String, fileName: String): String? {
        var result: String? = ""
        val file = File("$filePath/$fileName")
        var bufferedReader: BufferedReader? = null
        try {
            val StreamReader = InputStreamReader(FileInputStream(file), "gb2312")
            bufferedReader = BufferedReader(StreamReader)
            try {
                var read: String? = null
                while ({ read = bufferedReader.readLine();read }() != null) {
                    result = result + read+"\n"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close()
            }
        }
        return result
    }

    private fun division(result: String) {
        thread {
            try {
                val ct = result.split("高三期末考试作文：")
                for (ct1 in ct) {

                    Log.d("MainActivity",ct1)

                    }
                    //Log.d("MainActivity", ct1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendRequestWithOkHttp2(url: String,path: String,classification: String) {
        val StringBuffer = StringBuffer()
        val f = File(path);//新建文件
        if (!f.exists()) {
            f.mkdirs();
        }
        val file = File("/storage/emulated/0/zuowen/sj.txt")
        val bw = BufferedWriter(FileWriter(file))
            thread {
                try {
                    //Log.d("MainActivity", url)
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    val time = client.newCall(request).execute()
                    val bytes: ByteArray = time.body!!.bytes()
                    val page = String(bytes, Charset.forName("gb2312")).substringAfter("上一页")?.substringBefore("下一页").substringAfter("</a> <b>1</b> <a href=\"")
                    val rpage = page.split("<a href=\"")
                    val tpage = rpage[rpage.size-2].substringAfter(">").substringBefore("</a>").toInt()

                    //write(url,tpage, path,classification)

                    //val timebody = time.body?.string()

                    StringBuffer.append("页数：" + tpage + "\n")
                    StringBuffer.append("链接：" + url + "\n")
                    StringBuffer.append("路径：" + path + "\n")
                    StringBuffer.append("名称：" + classification + "\n")

                    //Log.d("MainActivity", tpage.toString())
                    //Log.d("MainActivity", url)
                    //Log.d("MainActivity", path)
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    //Log.d("MainActivity", e.message)
                }
                //Log.d("MainActivity", timebody.toString())
                /*
                for (i in 2 until 78) {
                    //http://www.zuowen.com/gaozhong/gaosan/xieren/index_3.shtml
                    val url =
                        "http://www.zuowen.com/gaozhong/gaosan/xieren/index_" + i + ".shtml"
                    //Log.d("MainActivity", url.toString())
                    try {
                        val client = OkHttpClient()
                        val request = Request.Builder()
                            .url(url)
                            .build()
                        val response = client.newCall(request).execute()
                        val bytes: ByteArray = response.body!!.bytes()
                        //val responseData = response.body?.string()
                        if (bytes != null) {
                            val ct = String(bytes, Charset.forName("gb2312")).substringAfter("<div class=\"artbox_l\">").substringBefore(
                                "上一页"
                            )
                            val ct1 = ct.split("<div class=\"artbox_l\">")
                            for (ct2 in ct1) {
                                val title = ct2.substringAfter("title=\"").substringBefore("\" target=")
                                val nofollow = ct2.substringAfter("=\"nofollow\">").substringBefore(
                                    "</a>"
                                )
                                val href = ct2.substringAfter("<a href=\"").substringBefore("\" title=")

                                //Log.d("MainActivity", title)
                                //Log.d("MainActivity", nofollow)

                                StringBuffer.append("链接：" + href + "\n")
                                StringBuffer.append("标题：" + title + "\n")
                                StringBuffer.append("内容：" + nofollow + "\n")

                                //Log.d("MainActivity", nofollow)
                            }
                            //Log.d("MainActivity", ct)
                        }
                    } catch (e: Exception) {
                        Log.d("MainActivity", e.message)
                    }
                }
                val f = File("/storage/emulated/0/xieren.txt");//新建文件
                val bw = BufferedWriter(FileWriter(f))
                bw.write(StringBuffer.toString());
                bw.flush();
                bw.close();
                //Log.d("MainActivity", StringBuffer.toString())
                */
            }
        Log.d("MainActivity", StringBuffer.toString())
        bw.write(StringBuffer.toString());
        bw.flush();
        bw.close();
        //Log.d("MainActivity", StringBuffer.toString())

        //Log.d("MainActivity", "ok")
    }
    private fun write(url: String, tpage: Int, path: String, classification:String) {
        val StringBuffer = StringBuffer()
        val f = File(path);//新建文件
        if (!f.exists()) {
            f.mkdirs();
        }
        val file = File(path+classification);
        val bw = BufferedWriter(FileWriter(file))
        thread {
            for (i in 2 until tpage) {
                val rurl = url + "index_" + i + ".shtml"
                Log.d("MainActivity", rurl)
                try {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url(rurl)
                        .build()
                    val response = client.newCall(request).execute()
                    val bytes: ByteArray = response.body!!.bytes()
                    //val responseData = response.body?.string()
                    if (bytes != null) {
                        val ct = String(
                            bytes,
                            Charset.forName("gb2312")
                        ).substringAfter("<div class=\"artbox_l\">").substringBefore(
                            "上一页"
                        )
                        val ct1 = ct.split("<div class=\"artbox_l\">")
                        for (ct2 in ct1) {
                            val title = ct2.substringAfter("title=\"").substringBefore("\" target=")
                            val nofollow = ct2.substringAfter("=\"nofollow\">").substringBefore(
                                "</a>"
                            )
                            val href = ct2.substringAfter("<a href=\"").substringBefore("\" title=")

                            //Log.d("MainActivity", title)
                            //Log.d("MainActivity", nofollow)

                            StringBuffer.append("链接：" + href + "\n")
                            StringBuffer.append("标题：" + title + "\n")
                            StringBuffer.append("内容：" + nofollow + "\n")

                            //Log.d("MainActivity", nofollow)
                        }
                        //Log.d("MainActivity", ct)
                    }
                } catch (e: Exception) {
                    Log.d("MainActivity", e.message)
                }
            }
        }
        bw.write(StringBuffer.toString());
        bw.flush();
        bw.close();
        //Log.d("MainActivity", "ok")

        //Log.d("MainActivity", StringBuffer.toString())

    }

    fun requestPermission(view: MainActivity) {
        val checkSelfPermission = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            //todo :has ready get permission write your code here

        } else {
            //requset permission
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && permissions[0] == Manifest.permission.READ_CONTACTS) {
                //todo:permission granted
                Toast.makeText(this@MainActivity, "permission granted", Toast.LENGTH_SHORT).show()
            } else{
                //todo:permission denied
                Toast.makeText(this@MainActivity, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showResponse(response: SpannableString) {
        runOnUiThread {
            // 在这里进行UI操作，将结果显示到界面上
            responseText.text = response
            val tpurl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg"
            Glide.with(this).load(tpurl).into(fruitImage);
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
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val version = jsonObject.getString("version")
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
            //showResponse(images)
            //Log.d("MainActivity", post)
            //Log.d("MainActivity", detail)
            //匹配文本内容
            val regExp1 = "(<text>)([\\s\\S])*?(</text>)"
            //匹配图片链接
            val regExp2 = "(<image>)([\\s\\S])*?(</image>)"
            //checkPhoneNum(detail, regExp1)
            //Setsort(checkPhoneNum(detail, regExp1), checkPhoneNum(detail, regExp2))
            /*
            Collections.sort(students, new Comparator<Student>()
            {
                public int compare(Student student1, Student student2)
                {
                    int io1 = addressOrder.indexOf(student1.getAddress());
                    int io2 = addressOrder.indexOf(student2.getAddress());
                    return io1 - io2;
                }
            });

             */
            //显示文本和图片
            placeImageView(Setsort(checkPhoneNum(detail, regExp1), checkPhoneNum(detail, regExp2)))


            val resources: Resources = this.getResources()
            //Log.d("MainActivity", detail)

            val span3 = SpannableString(detail)
            val image = ImageSpan(this, R.drawable.img_1, DynamicDrawableSpan.ALIGN_BOTTOM)
            span3.setSpan(image, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            //tv3.setText(span3)

            //showResponse(span3)

            //val comments = jsonObject.getString("comments")
            val jsonArray = JSONArray(images)
            //Log.d("MainActivity", comments)
            for (i in 0 until jsonArray.length()) {
                val pictureurl = jsonArray.getString(i)
                //val jsonObject2 = jsonArray.getJSONObject(i)
                //val pictureurl = jsonObject2.get()
                //val name = jsonObject.get("comments")
                //val text = jsonObject.getString("images")

                //Log.d("MainActivity", "pictureurl is $pictureurl")

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
            Log.d("MainActivity", "id is ${app.id}")
            Log.d("MainActivity", "name is ${app.name}")
            Log.d("MainActivity", "version is ${app.version}")
        }
    }

    fun checkPhoneNum(num: String, regExp: String): MutableList<Content> {
        //val regExp1 = "(<text>)([\\s\\S])*?(</text>)"
        //val regExp2 = "(<image>)([\\s\\S])*?(</image>)"
        val p = Pattern.compile(regExp)
        val m = p.matcher(num)
        val strArray: Array<String>
        val mutList = mutableListOf<Content>()
        while (m.find()) {
            //Log.d("MainActivity", m.group().toString())

            mutList.add(Content(m.group(), m.start(), m.end()))
            //Log.d("MainActivity", "位置")
            //Log.d("MainActivity", m.start().toString())
            //Log.d("MainActivity", m.end().toString())

            //placeImageView()

        }
        return mutList
        //return m.matches()
    }

    //集合排序
    fun Setsort(list1: MutableList<Content>, list2: MutableList<Content>): MutableList<Content> {
        val nmutList = mutableListOf<Content>()
        //nmutList.add(list1)
        //nmutList.add(list2)
        for (l1 in list1) {
            nmutList.add(l1)
            //Log.d("Setsort", l)
        }
        for (l2 in list2) {
            nmutList.add(l2)
            //Log.d("Setsort", l)
        }
        /*
        for (l3 in nmutList) {
            //Log.d("Setsort", l3.start.toString())
            //Log.d("Setsort", l3.end.toString())
            Log.d("Setsort", l3.content)
        }

         */

        Collections.sort(nmutList, Comparator<Content> { o1, o2 ->
            //此处创建了一个匿名内部类
            o1.start - o2.start
        })
        //Log.d("Setsort", "------------------------")

        for (l3 in nmutList) {
            //Log.d("Setsort", l3.start.toString())
            //Log.d("Setsort", l3.end.toString())
            Log.d("Setsort", l3.content)
        }
        return nmutList
    }

    //添加图片
    fun placeImageView(mutList: MutableList<Content>) {
        runOnUiThread {
            // 在这里进行UI操作，将结果显示到界面上
            //Log.d("MainActivity", mutList.size.toString())
            //Log.d("MainActivity", mutList2.size.toString())

            for (content in mutList) {

                when(content.content.contains("<text>")){
                    true -> placeTextview(content)
                    false -> placeImageView(content)
                     }

                //val tpurl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg"
                //Log.d("MainActivity",tp.toString().substringAfter("<image>").substringBefore("</image>"))
                //Glide.with(this).load(tp.toString().substringAfter("<image>").substringBefore("</image>").substringBefore(",")).into(imageView);

            }
        }
    }
    fun placeTextview(content: Content) {
        runOnUiThread {
            // 在这里进行UI操作，将结果显示到界面上
            val textView = TextView(this)
            textView.setText(content.content.substringAfter("<text>").substringBefore("</text>"))
            textView.setTextColor(Color.BLACK)
            textView.setTextSize(18F)
            linear.addView(textView)
        }
    }

    fun placeImageView(content: Content) {
            runOnUiThread {
                // 在这里进行UI操作，将结果显示到界面上
                val imageView = ImageView(this)
                Glide.with(this)
                    .load(
                        content.content.substringAfter("<image>").substringBefore("</image>")
                            .substringBefore(
                                ","
                            )
                    )
                    //.placeholder(R.drawable.loading)
                    //.error(R.drawable.error)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.override(tp.toString().substringAfter("<image>").substringBefore("</image>").substringBefore(",").toInt(), 100)//指定图片大小
                    .into(imageView);
                linear.addView(imageView)
            }
    }


}


