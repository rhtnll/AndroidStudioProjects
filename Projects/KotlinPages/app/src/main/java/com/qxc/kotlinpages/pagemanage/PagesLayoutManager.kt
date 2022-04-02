package com.qxc.kotlinpages.pagemanage

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * 分页布局管理类：
 * 1、创建列表布局、网格布局
 * 2、记录数据位置（用于切换列表布局、网格布局时，保持位置不变）
 *
 * @author 齐行超
 * @date 19.11.30
 */
class PagesLayoutManager(
    pcontext: Context
) {
    val STYLE_LIST = 1 //列表样式(常量标识)
    val STYLE_GRID = 2 //网格样式(常量标识)

    var llManager: LinearLayoutManager //列表布局管理器对象
    var glManager: GridLayoutManager //网格布局管理器对象

    var position: Int = 0 //数据位置（当切换样式时，需记录数据的位置，用以保持数据位置不变）
    var context = pcontext //上下文对象

    init {
        llManager = LinearLayoutManager(context)
        glManager = GridLayoutManager(context, 2)
    }

    /**
     * 获得布局管理器对象
     */
    fun getLayoutManager(pagesStyle: Int): LinearLayoutManager {
        //记录当前数据位置
        recordDataPosition(pagesStyle)

        //根据样式返回布局管理器对象
        if (pagesStyle == STYLE_LIST) {
            return llManager
        }
        return glManager
    }

    /**
     * 获得数据位置
     */
    fun getDataPosition(): Int {
        return position
    }

    /**
     * 记录数据位置
     */
    private fun recordDataPosition(pagesStyle: Int) {
        //pagesStyle表示目标样式，此处需要记录的是原样式时的数据位置
        if (pagesStyle == STYLE_LIST) {
            position = glManager?.findFirstVisibleItemPosition()
        } else if (pagesStyle == STYLE_GRID) {
            position = llManager?.findFirstVisibleItemPosition()
        }
    }
}