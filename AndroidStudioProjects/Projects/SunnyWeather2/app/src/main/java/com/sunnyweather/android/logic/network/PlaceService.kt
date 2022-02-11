package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    //@GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    //fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

    //http://search.huluxia.com/game/search/ANDROID/1.1?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=&device_code=%5Bw%5D00%3A81%3A52%3A10%3Acd%3Af5-%5Bi%5D865166026897317-%5Bs%5D89860006519372881076&start=0&count=20&keyword=%E6%88%91%E7%9A%84%E4%B8%96%E7%95%8C

    @GET("/game/search/ANDROID/1.1?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=&device_code=%5Bw%5D00%3A81%3A52%3A10%3Acd%3Af5-%5Bi%5D865166026897317-%5Bs%5D89860006519372881076&count=20")
    fun searchPlaces(@Query("start") start: Int,@Query("keyword") query: String): Call<PlaceResponse>

}