package com.qxc.kotlinpages.pagemanage

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.qxc.kotlinpages.pagedata.DataInfo
import com.qxc.kotlinpages.pagedata.DataSearch

/**
 * 分页数据管理类：
 * 1、分页数据的查询位置、每页数量
 * 2、分页数据接口查询
 * 3、分页状态文本处理
 *
 * @author 齐行超
 * @date 19.11.30
 */
class PagesDataManager {
    var startIndex = 0 //分页起始位置
    val pageNum = 10 //每页数量
    val TYPE_PAGE_MORE = "数据加载中..." //分页加载类型：更多数据
    val TYPE_PAGE_LAST = "没有更多数据了" //分页加载类型：没有数据了
    val TYPE_PAGE_ERROR = "出错了，点击重试！！" //分页加载类型：出错了，当这种状态时可点击重试

    //定义数据回调监听
    //参数：dataInfos 当前页数据集合， footType 分页状态文本
    lateinit var listener: ((dataInfos: ArrayList<DataInfo>, footType: String) -> Unit)

    /**
     * 设置回调
     */
    fun setDataListener(pListener: ((dataInfos: ArrayList<DataInfo>, footType: String) -> Unit)) {
        this.listener = pListener
    }

    /**
     * 查询数据
     */
    fun searchPagesData() {
        //创建数据查询对象（模拟数据查询）
        var dataSearch = DataSearch()
        //设置数据回调监听
        dataSearch.setDataRequestListener { errorCode, datas ->
            //切回主线程
            Handler(Looper.getMainLooper()).post {
                if (errorCode == 0) {
                    //累计当前位置
                    startIndex += datas.size
                    //判断后面是否还有数据
                    var footType = if (pageNum == datas.size) TYPE_PAGE_MORE else TYPE_PAGE_LAST
                    //回调结果
                    listener.invoke(datas, footType)
                } else {
                    //回调错误结果
                    listener.invoke(datas, TYPE_PAGE_ERROR)
                }
            }
        }
        //查询数据
        dataSearch.search(startIndex, pageNum)
    }

    /**
     * 重置查询
     */
    fun reset() {
        startIndex = 0;
    }
}