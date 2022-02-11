package com.androdocs.populatelistview.logic.model

class PlaceResponse(val status: String, val gameapps: List<gameapp>)
class gameapp(val post: String)

//class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

//class Location(val lng: String, val lat: String)