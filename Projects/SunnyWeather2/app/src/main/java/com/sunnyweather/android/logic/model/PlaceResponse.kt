package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName



class PlaceResponse(val gameapps: List<App>, val start: Int, val status: Int)

class App(val appid: String, val applogo: String, val appsize: Double, val apptitle: String, val categoryname: String, val localurl: Localurl)

class Localurl(val name: String, val url: String, val urlType: String)

/*
class PlaceResponse(val status: String, val places: List<Place>)

class Place(val appid: String, val location: Location, val apptitle: String, val pageSize: Long)

class Location(val name: String, val url: String, val urlType: String)



class PlaceResponse(val status: String, val places: List<Place>)

class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

class Location(val lng: String, val lat: String)
 */
