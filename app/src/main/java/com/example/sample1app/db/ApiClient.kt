package com.example.sample1app.db

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    //パラメータ指定
    @GET("hotpepper/gourmet/v1")
    //多分モデルを引き出してる
    fun apiDemo(
        @Query("lat") lat: Int,
        @Query("lng") lng: Int,
        @Query("range") range: Int
    ): Call<List<SampleResponse>>

}