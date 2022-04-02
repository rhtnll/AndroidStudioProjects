package com.example.recyclerviewtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val fruitList = ArrayList<Composition>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initFruits() // 初始化水果数据

        readTxtFile("", "")?.let { division(it) }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //val layoutManager = GridLayoutManager(this, 4)
        //val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(fruitList)
        recyclerView.adapter = adapter
    }

    @Throws(Exception::class)
    fun readTxtFile(filePath: String, fileName: String): String? {
        var result: String? = ""
        //val file = File("$filePath/$fileName")
        var bufferedReader: BufferedReader? = null
        try {
            //val StreamReader = InputStreamReader(FileInputStream(file), "gb2312")

            val StreamReader = InputStreamReader(resources.openRawResource(R.raw.xr), "gb2312")
            bufferedReader = BufferedReader(StreamReader)
            try {
                var read: String? = null
                while ({ read = bufferedReader.readLine();read }() != null) {
                    result = result + read + "\n"
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
                val ct = result.substringAfter("高三写人作文：").split("高三写人作文：")

                for (ct1 in ct) {
                    val ct2 = ct1.split("\n")
                    //Log.d("MainActivity",ct1)
                    fruitList.add(
                        Composition(
                            ct2 as java.util.ArrayList<String>,
                            ct2[0],
                            ct2[2].trim()
                        )
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
