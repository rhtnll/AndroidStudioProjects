package com.example.networktest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ShowActivity : AppCompatActivity() {
    //初始化，有add，remove方法的集合
    var list=mutableListOf<String>()
    //初始化必须赋值，只读模式
    var data= listOf(
        "瓦洛兰",
        "德玛西亚",
        "班德尔城",
        "诺克萨斯",
        "祖安",
        "瓦洛兰",
        "德玛西亚",
        "班德尔城",
        "诺克萨斯",
        "祖安")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        initUI()
    }

    private fun initUI() {
        val layoutManager=LinearLayoutManager(this)
//        val layout=GridLayoutManager(this,2);
        rv_list.layoutManager=layoutManager
        var adapter=RvAdapter(data)
        rv_list.adapter=adapter

        sw_refresh.setOnRefreshListener {
            sw_refresh.isRefreshing=false
        }
    }
}