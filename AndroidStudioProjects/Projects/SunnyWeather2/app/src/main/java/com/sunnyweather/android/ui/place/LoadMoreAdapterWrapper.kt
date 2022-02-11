package com.sunnyweather.android.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R

class LoadMoreAdapterWrapper(adapter: BaseAdapter, onLoad: OnLoad?) :
    BaseAdapter<String?>() {
    private val bAdapter: BaseAdapter
    private var mPagePosition = 0
    private var hasMoreData = true
    private val mOnLoad: OnLoad?
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.list_item_no_more) {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            NoMoreItemVH(view)
        } else if (viewType == R.layout.list_item_loading) {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            LoadingItemVH(view)
        } else {
            bAdapter.onCreateViewHolder(parent, viewType)
        }
    }

    fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is LoadingItemVH) {
            requestData(mPagePosition, mPageSize)
        } else if (holder is NoMoreItemVH) {
        } else {
            bAdapter.onBindViewHolder(holder, position)
        }
    }

    private fun requestData(pagePosition: Int, pageSize: Int) {

        //网络请求,如果是异步请求，则在成功之后的回调中添加数据，并且调用notifyDataSetChanged方法，hasMoreData为true
        //如果没有数据了，则hasMoreData为false，然后通知变化，更新recylerview
        mOnLoad?.load(pagePosition, pageSize, object : ILoadCallback {
            override fun onSuccess() {
                notifyDataSetChanged()
                mPagePosition = (mPagePosition + 1) * mPageSize
                hasMoreData = true
            }

            override fun onFailure() {
                hasMoreData = false
            }
        })
    }

    fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            if (hasMoreData) {
                R.layout.list_item_loading
            } else {
                R.layout.list_item_no_more
            }
        } else {
            bAdapter.getItemViewType(position)
        }
    }

    val itemCount: Int
        get() = bAdapter.getItemCount() + 1

    internal class LoadingItemVH(itemView: View?) : RecyclerView.ViewHolder(itemView)
    internal class NoMoreItemVH(itemView: View?) : RecyclerView.ViewHolder(itemView)
    companion object {
        private const val mPageSize = 10
    }

    init {
        bAdapter = adapter
        mOnLoad = onLoad
    }
}

internal interface OnLoad {
    fun load(pagePosition: Int, pageSize: Int, callback: ILoadCallback?)
}

internal interface ILoadCallback {
    fun onSuccess()
    fun onFailure()
}