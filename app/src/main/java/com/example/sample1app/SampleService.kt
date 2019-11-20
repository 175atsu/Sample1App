package com.example.sample1app

import com.example.sample1app.db.ApiClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class SampleService {


    companion object {
        private const val END_POINT = "http://webservice.recruit.co.jp/"
    }

    var retrofit: Retrofit

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        //addConverterFactoryは
        this.retrofit = Retrofit.Builder()
            .baseUrl(END_POINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    //Clientを作成
    val httpBuilder: OkHttpClient.Builder get() {
        val httpClient = OkHttpClient.Builder()
        //headerの追加
        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .method(original.method(), original.body())
                .build()

            var response = chain.proceed(request)

            return@Interceptor response
        })
            .readTimeout(30, TimeUnit.SECONDS)

        //log
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }

    fun createService(): ApiClient {
        //クライアント生成
        var client = httpBuilder.build()
        var retrofit = Retrofit.Builder()
            .baseUrl(END_POINT)//基本のurl設定
            .addConverterFactory(GsonConverterFactory.create())//Gsonの使用
            .client(client)//カスタマイズしたokhttpのクライアントの設定
            .build()
        //Interfaceから実装を取得
        var API = retrofit.create(ApiClient::class.java)

        return API
    }


}