package com.androdocs.populatelistview.logic.network

import com.androdocs.populatelistview.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    //@GET("v2/place?token=${MyApplication.TOKEN}&lang=zh_CN")
    @GET("/game/search/ANDROID/1.1?platform=2&gkey=000000&app_version=4.0.0.5.3&versioncode=20141430&market_id=floor_web&_key=6D97B057C686648A57A7C6FD632AAFE7B5470BA61633958DF391F19B17135BBB2EF819FF52D0B50F89DE376A3FD84A0FF4254154817322F9&device_code=%5Bw%5D02%3A00%3A00%3A00%3A00%3A00%5Bd%5D9a1874be-e845-4a13-9483-c8dabda034d8&start=0&count=20")
    fun searchPlaces(@Query("keyword") keyword: String): Call<PlaceResponse>

}