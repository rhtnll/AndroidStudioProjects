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
            return URL("https://www.androdocs.com/files/uploads/original/sample-json-data-1567767983.txt").readText(
                    Charsets.UTF_8
                )
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)
            val usersArr = jsonObj.getJSONArray("users")
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)

                val map = HashMap<String, String>()
                map["name"] = singleUser.getString("name")
                map["age"] = singleUser.getString("age")
                map["city"] = singleUser.getString("city")
                map["image"] = singleUser.getString("image")
                Log.d("MainActivity",map["image"])
                dataList.add(map)
            }

            findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList)
        }
    }
}
