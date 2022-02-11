package com.qxc.kotlinpages.pagedata

import com.qxc.kotlinpages.utils.AppUtils

/**
 * 数据查询类：模拟网络请求，从服务器数据库获取数据
 *
 * @author 齐行超
 * @date 19.11.30
 */
class DataSearch {
    //服务器数据库中的数据总数量(模拟)
    private val totalNum = 25

    //声明回调函数（Lambda表达式参数：errorCode错误码，dataInfos数据，无返回值）
    private lateinit var listener: (errorCode: Int, dataInfos: ArrayList<DataInfo>) -> Unit

    /**
     * 设置数据请求监听器
     *
     * @param plistener 数据请求监听器的回调类对象
     */
    fun setDataRequestListener(plistener: (errorCode: Int, dataInfos: ArrayList<DataInfo>) -> Unit) {
        this.listener = plistener
    }

    /**
     * 查询方法（模拟从数据库查询）
     * positionNum: 数据起始位置
     * pageNum: 查询数量（每页查询数量）
     */
    fun search(positionNum: Int, pageNum: Int) {
        //模拟网络异步请求（子线程中，进行异步请求）
        Thread()
        {
            //模拟网络查询耗时
            Thread.sleep(1000)

            //获得数据查询结束位置
            var end: Int = if (positionNum + pageNum > totalNum) totalNum else positionNum + pageNum
            //创建集合
            var datas = ArrayList<DataInfo>()

            //判断网络状态
            if (!AppUtils.instance.isConnectNetWork()) {
                //回调异常结果
                this.listener(1,datas)
                //回调异常结果
                //this.listener.invoke(-1, datas)
                return@Thread
            }

            //组织数据（模拟获取到数据）
            for (index in positionNum..end - 1) {
                var data = DataInfo()
                data.title = "Android Kotlin ${index + 1}"
                data.desc = "Kotlin 是一个用于现代多平台应用的静态编程语言，由 JetBrains 开发..."
                data.price = (100 * (index + 1)).toString()
                data.imageUrl = ""
                data.link = "跳转至Kotlin柜台 -> JetBrains"
                datas.add(data)
            }

            //回调结果
            this.listener.invoke(0, datas)

        }.start()
    }
}