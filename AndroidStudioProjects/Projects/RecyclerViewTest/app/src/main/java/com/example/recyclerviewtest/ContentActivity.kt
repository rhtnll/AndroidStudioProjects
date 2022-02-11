package com.example.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList
import kotlin.concurrent.thread

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content)
        init()
    }

    private fun init() {
            try {
                val back: ImageView = findViewById(R.id.back)
                back.setOnClickListener(){
                    this.finish()
                }
                plist(intent.getStringArrayListExtra("data"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    private fun plist(list :ArrayList<String>) {
        try {
            val title: TextView = findViewById(R.id.title)
            val time: TextView = findViewById(R.id.time)
            val content: TextView = findViewById(R.id.content)
            title.setText(list[0])
            time.setText(list[1])
            content.setText("")
            repeat(2){
                list.removeAt(0)
            }
            for (l in list){
                //Log.d("MainActivity",content)
                content.append("    "+l.trim())
                content.append("\n")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}