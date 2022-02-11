package com.example.networktest

import android.view.View
import android.view.ViewGroup

class RvAdapter(var date : List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val  inflate = from(parent?.context).inflate(R.layout.item_layout, null)
            return MyHolder(inflate)
        }else{
            val inflate = from(parent?.context).inflate(R.layout.item_layout, null)
            return MyHolder2(inflate)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is MyHolder){
            holder.bind(date[position])
        }else if(holder is MyHolder2){
            holder.bind(date[position])
        }
    }

    override fun getItemCount(): Int {
        return date.size
    }

    override fun getItemViewType(position: Int): Int {
        when(position){
            //多布局
            in 1..2 ->
                return 1
            else -> return super.getItemViewType(position)
        }
    }

    class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(date: String){
            itemView.tv_name.text=date
            itemView.tv_class.text=date
            itemView.tv_time.text=date
        }
    }

    class MyHolder2(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(date: String){
            itemView.tv_content.text=date
            itemView.tv_name.text=date
        }
    }
}