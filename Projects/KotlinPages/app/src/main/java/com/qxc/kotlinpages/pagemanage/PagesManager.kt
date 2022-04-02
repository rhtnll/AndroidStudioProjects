package com.qxc.kotlinpages.pagemanage

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qxc.kotlinpages.adapter.PagesAdapter
import com.qxc.kotlinpages.pagedata.DataInfo

/**
 * 分页管理类：
 * 1、创建适配器
 * 2、查询、绑定分页数据
 * 3、切换分页布局
 * 4、当切换至网格布局时，设置footview独占一行
 *
 * @author 齐行超
 * @date 19.11.30
 */
class PagesManager(pContext: Context, pRecyclerView: RecyclerView) {
    private var context = pContext //上下文对象
    private var recyclerView = pRecyclerView //列表控件对象
    private var adapter:PagesAdapter? = null //适配器对象
    private var pagesLayoutManager = PagesLayoutManager(context) //分页布局管理对象
    private var pagesDataManager = PagesDataManager() //分页数据管理对象
    private var datas = ArrayList<DataInfo>() //数据集合

    /**
     * 设置分页样式（列表、网格）
     *
     * @param isGrid 是否网格样式
     */
    fun setPagesStyle(isGrid: Boolean): PagesManager {
        //通过样式获得对应的布局类型
        var style = if (isGrid) pagesLayoutManager.STYLE_GRID else pagesLayoutManager.STYLE_LIST
        var layoutManager = pagesLayoutManager.getLayoutManager(style)

        //获得当前数据位置（切换样式后，滑动到记录的数据位置）
        var position = pagesLayoutManager.getDataPosition()

        //创建适配器对象
        adapter = PagesAdapter(context, datas, pagesDataManager.TYPE_PAGE_MORE)
        //通知适配器，itemview当前使用哪种分页布局样式（列表、网格）
        adapter?.setItemStyle(style)

        //列表控件设置适配器
        recyclerView.adapter = adapter
        //列表控件设置布局管理器
        recyclerView.layoutManager = layoutManager
        //列表控件滑动到指定位置
        recyclerView.scrollToPosition(position)

        //当layoutManager是网格布局时，设置footview独占一行
        if(layoutManager is GridLayoutManager){
            setSpanSizeLookup(layoutManager)
        }

        //设置监听器
        setListener()
        return this
    }

    /**
     * 设置监听器：
     *
     * 1、当滑动到列表底部时，查询下一页数据
     * 2、当点击了footview的"出错了，点击重试！！"时，重新查询数据
     */
    fun setListener() {
        //1、当滑动到列表底部时，查询下一页数据
        adapter?.setOnFootViewAttachedToWindowListener {
            //查询数据
            searchData()
        }

        //2、当点击了footview的"出错了，点击重试！！"时，重新查询数据
        adapter?.setOnFootViewClickListener {
            if (it.equals(pagesDataManager.TYPE_PAGE_ERROR)) {

                //点击查询，更改footview状态
                adapter?.footMessage = pagesDataManager.TYPE_PAGE_MORE
                adapter?.notifyDataSetChanged()

                //"出错了，点击重试！！
                searchData()
            }
        }
    }

    /**
     * 设置grid footview独占一行
     */
    fun setSpanSizeLookup(layoutManager: GridLayoutManager) {
        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter?.TYPE_FOOTVIEW == adapter?.getItemViewType(position)) layoutManager.getSpanCount() else 1
            }
        })
    }

    /**
     * 获得查询结果，刷新列表
     */
    fun searchData() {
        pagesDataManager.setDataListener { pdatas, footMessage ->
            if (pdatas != null) {
                datas.addAll(pdatas)
                adapter?.footMessage = footMessage
                adapter?.notifyDataSetChanged()
            }
        }
        pagesDataManager.searchPagesData()
    }
}