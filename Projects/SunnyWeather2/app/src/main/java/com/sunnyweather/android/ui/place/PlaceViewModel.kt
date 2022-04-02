package com.sunnyweather.android.ui.place

import androidx.lifecycle.*
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.App
import com.sunnyweather.android.logic.model.PlaceResponse

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    var start = 0

    val placeList = ArrayList<App>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(this.start, query)
    }

    fun searchPlaces(start: Int, query: String) {
        searchLiveData.value = query
        this.start = start
    }


}