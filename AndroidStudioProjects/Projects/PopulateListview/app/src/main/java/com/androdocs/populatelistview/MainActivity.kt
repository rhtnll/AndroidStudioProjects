package com.androdocs.populatelistview

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchJsonData().execute()
    }


    inner class fetchJsonData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            //return URL("http://blog.jjidc.xyz:2020/test.json").readText(
                //Charsets.UTF_8)
            return URL("http://floor.huluxia.com/post/list/ANDROID/2.1?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=6D97B057C686648A57A7C6FD632AAFE7B5470BA61633958DF391F19B17135BBB2EF819FF52D0B50F89DE376A3FD84A0FF4254154817322F9&device_code=%5Bw%5D02%3A00%3A00%3A00%3A00%3A00%5Bd%5D9a1874be-e845-4a13-9483-c8dabda034d8&start=0&count=20&cat_id=96&tag_id=0&sort_by=0").readText(
                Charsets.UTF_8)
            /*
            return URL("https://www.androdocs.com/files/uploads/original/sample-json-data-1567767983.txt").readText(
                    Charsets.UTF_8
                )

             */
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)
            //jsonObj.getString("posts")
            //Log.d("MainActivity", jsonObj.toString())

            val usersArr = jsonObj.getJSONArray("posts")
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)

                val map = HashMap<String, String>()
                map["postID"] = singleUser.getString("postID")
                map["title"] = singleUser.getString("title")
                //map["detail"] = singleUser.getString("detail")
                map["images"] = singleUser.getString("images")
                //map["image"] = "https://www.androdocs.com/files/uploads/small/ananya-json-tutorial-image-1567766865.jpg"

                val jsonArray = singleUser.getJSONArray("images")
                //Log.d("MainActivity", jsonArray.length().toString())

                //Log.d("MainActivity", jsonArray.toString())
                //jsonArray.length()
                if (jsonArray.length() > 0) {
                    Log.d("MainActivity", jsonArray.getString(0))
                    map["image"] = jsonArray.getString(0)
                    for (j in 0 until jsonArray.length()) {
                        val pictureurl = jsonArray.getString(j)
                        //map["image"] = jsonArray.getString(0)
                        //val jsonObject2 = jsonArray.getJSONObject(i)
                        //val pictureur2 = jsonArray.get(0)
                        //val name = jsonObject.get("comments")
                        //val text = jsonObject.getString("images")
                        //Log.d("MainActivity", "pictureurl is $pictureurl")
                        //Log.d("MainActivity", jsonObject2.toString())
                        //Log.d("MainActivity", "version is $version")
                    }
                }
                //map["user"] = singleUser.getString("user")
                dataList.add(map)
            }

            findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList)

        }

    }
}
