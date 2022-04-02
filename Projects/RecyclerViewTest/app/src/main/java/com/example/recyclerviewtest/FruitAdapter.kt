package com.example.recyclerviewtest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList


class FruitAdapter(val compositionList: List<Composition>) : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val content: TextView = view.findViewById(R.id.content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.composition_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = compositionList[position]
            val intent = Intent(parent.context, ContentActivity::class.java)
            intent.putStringArrayListExtra("data", fruit.composition)
            parent.context.startActivity(intent)
            Toast.makeText(parent.context, "you clicked view ${fruit.title}", Toast.LENGTH_SHORT).show()
        }
        viewHolder.content.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = compositionList[position]
            Toast.makeText(parent.context, "you clicked content ${fruit.title}", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = compositionList[position]
        //holder.fruitImage.setImageResource(fruit.imageId)
        holder.title.text = fruit.title
        holder.content.text = fruit.content
    }

    override fun getItemCount() = compositionList.size

}