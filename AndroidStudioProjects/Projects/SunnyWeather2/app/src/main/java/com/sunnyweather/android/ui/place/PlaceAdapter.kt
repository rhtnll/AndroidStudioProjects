package com.sunnyweather.android.ui.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sunnyweather.android.R
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.App
import kotlinx.android.synthetic.main.activity_weather.*

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<App>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val categoryname: TextView = view.findViewById(R.id.categoryname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            //val position = holder.adapterPosition
            //val place = placeList[position]
            //val activity = fragment.activity
            /*
            val intent = Intent(parent.context, Weather::class.java).apply {
                putExtra("localurl_url", place.localurl.url)
            }
                fragment.startActivity(intent)
                activity?.finish()
                
             */
            }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.apptitle
        holder.placeAddress.text = place.appsize.toString()+"MB"
        Glide.with(SunnyWeatherApplication.context)
            .load(place.applogo)
            .override(500, 500)
            .into(holder.imageView);
        holder.categoryname.text = place.categoryname
    }

    override fun getItemCount() = placeList.size
}
