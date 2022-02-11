package com.example.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IRetrofitService {
    @GET
    Call<Bean> getUrl(@Url String url);
}
