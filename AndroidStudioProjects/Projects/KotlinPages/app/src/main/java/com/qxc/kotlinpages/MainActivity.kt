package com.qxc.kotlinpages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qxc.kotlinpages.pagemanage.PagesManager
import com.qxc.kotlinpages.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var isGrid = false
    var pagesManager: PagesManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppUtils.register(this)

        initEvent()
        initData()
    }

    fun initEvent() {
        //切换列表样式按钮的点击事件
        iv_style.setOnClickListener {
            //切换图标（列表与网格）
            var id: Int =
                if (isGrid) R.mipmap.product_search_list_style_grid else R.mipmap.product_search_list_style_list
            iv_style.setImageResource(id)

            //记录当前图标类型
            isGrid = !isGrid

            //更改样式（列表与网格）
            pagesManager!!.setPagesStyle(isGrid)
        }
    }

    fun initData() {
        pagesManager = PagesManager(this, rv_data)
        pagesManager!!.setPagesStyle(isGrid).searchData()
    }
}
