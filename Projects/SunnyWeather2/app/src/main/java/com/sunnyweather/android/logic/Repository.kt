package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.model.PlaceResponse
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

    fun searchPlaces(start: Int, query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(start, query)
            //Log.d("TEST",placeResponse.status.toString())
            if (placeResponse.status == 1) {
                //val places = placeResponse.gameapps
                //Log.d("TEST",placeResponse)
                Result.success(placeResponse)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<PlaceResponse>(e)
        }
        emit(result as Result<PlaceResponse>)
    }

}