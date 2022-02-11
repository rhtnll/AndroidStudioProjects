package com.qxc.kotlinpages.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qxc.kotlinpages.R
import com.qxc.kotlinpages.pagedata.DataInfo

/**
 * 分页适配器类
 * 1、创建、绑定itemview（列表item、网格item）、footview
 * 2、判断是否滑动到列表底部
 * 3、footview点击事件回调
 * 4、滑动到列表底部事件回调
 *
 * @author 齐行超
 * @date 19.11.30
 */
class PagesAdapter(
    pContext: Context,
    pDataInfos: ArrayList<DataInfo>,
    pFootMessage : String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context = pContext //上下文对象，通过构造传函数递过来
    private var datas = pDataInfos //数据集合，通过构造函数传递过来
    var footMessage = pFootMessage //footview文本信息，可通过构造函数传递过来，也可再次修改

    val TYPE_FOOTVIEW: Int = 1 //item类型：footview
    val TYPE_ITEMVIEW: Int = 2 //item类型：itemview
    var typeItem = TYPE_ITEMVIEW

    val STYLE_LIST_ITEM = 1 //样式类型：列表
    val STYLE_GRID_ITEM = 2 //样式类型：网格
    var styleItem = STYLE_LIST_ITEM

    /**
     * 重写创建ViewHolder的函数
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //如果是itemview
        if (typeItem == TYPE_ITEMVIEW) {
            //判断样式类型（列表布局、网格布局）
            var layoutId =
                if (styleItem == STYLE_LIST_ITEM) R.layout.item_page_list else R.layout.item_page_grid
            var view = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return ItemViewHolder(view)
        }
        //如果是footview
        else {
            var view = LayoutInflater.from(context).inflate(R.layout.item_page_foot, parent, false)
            return FootViewHolder(view)
        }
    }

    /**
     * 重写获得项数量的函数
     */
    override fun getItemCount(): Int {
        //因列表中增加了footview（显示分页状态信息），所以item总数量 = 数据数量 + 1
        return datas.size + 1
    }

    /**
     * 重写绑定ViewHolder的函数
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            if (datas.size <= position) {
                return
            }
            var data = datas.get(position)
            holder.tv_title.text = data.title
            holder.tv_desc.text = data.desc
            holder.tv_price.text = data.price
            holder.tv_link.text = data.link

        } else if (holder is FootViewHolder) {
            holder.tv_msg.text = footMessage

            //当点击footview时，将该事件回调出去
            holder.tv_msg.setOnClickListener {
                footViewClickListener.invoke(footMessage)
            }
        }
    }

    /**
     * 重新获得项类型的函数（项类型包括：itemview、footview）
     */
    override fun getItemViewType(position: Int): Int {
        //设置在数据最底部显示footview
        typeItem = if (position == datas.size) TYPE_FOOTVIEW else TYPE_ITEMVIEW
        return typeItem
    }


    /**
     * 当footview第二次出现在列表时，回调该事件（此处用于模拟用户上滑手势，当滑到底部时，重新请求数据）
     */
    var footviewPosition = 0
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (footviewPosition == holder.adapterPosition) {
            return
        }
        if (holder is FootViewHolder) {
            footviewPosition = holder.adapterPosition
            //回调查询事件
            footViewAttachedToWindowListener.invoke()
        }
    }

    /**
     * ItemViewHolder
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        var tv_desc = itemView.findViewById<TextView>(R.id.tv_desc)
        var tv_price = itemView.findViewById<TextView>(R.id.tv_price)
        var tv_link = itemView.findViewById<TextView>(R.id.tv_link)
    }

    /**
     * FootViewHolder
     */
    class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_msg = itemView.findViewById<TextView>(R.id.tv_msg)
    }

    /**
     * 设置Item样式（列表、网格）
     */
    fun setItemStyle(pstyle: Int) {
        styleItem = pstyle
    }

    //定义footview附加到Window上时的回调
    lateinit var footViewAttachedToWindowListener: () -> Unit
    fun setOnFootViewAttachedToWindowListener(pListener: () -> Unit) {
        this.footViewAttachedToWindowListener = pListener
    }

    //定义footview点击时的回调
    lateinit var footViewClickListener:(String)->Unit
    fun setOnFootViewClickListener(pListner:(String)->Unit){
        this.footViewClickListener = pListner
    }
}