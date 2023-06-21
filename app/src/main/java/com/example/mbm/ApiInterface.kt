package com.example.mbm

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mbm.home.MenuResponse
import com.example.mbm.home.ResponseHome
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

    companion object {
//        const val HEADERS: String = "ApiKey:${Config.API_KEY}"
        const val HEADERS_CONTENT_TYPE: String = "Content-Type:application/json"

        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(MyApp.context))
            .connectTimeout(30, TimeUnit.SECONDS) // Increase the connection timeout
            .readTimeout(30, TimeUnit.SECONDS) // Increase the read timeout
            .writeTimeout(30, TimeUnit.SECONDS) // Increase the write timeout
            .build()


        var BASE_URL = "http://193.123.66.243:5000/"
        const val DMS_URL = "http://193.123.66.243:5000/"
       // var BASE_URL = "http://localhost:62200/"
       // const val DMS_URL = "http://localhost:62200/"




        fun create(): ApiInterface {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .client(client)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        fun createDMS(): ApiInterface {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .client(client)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(DMS_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }


//    @FormUrlEncoded
//    @POST("api/datasetjson")
//    fun getData(
//        @Field("Key") key: String,
//        @Field("Host") host: String,
//        @Field("Port") port: String,
//        @Field("User") user: String,
//        @Field("Password") pass: String,
//        @Field("ServiceName") service: String,
//        @Field("Query") query: String,
//    ): Call<Response>

    @FormUrlEncoded
    @POST("api/datasetjson")
    fun getData2(
        @Field("Key") key: String,
        @Field("Host") host: String,
        @Field("Port") port: String,
        @Field("User") user: String,
        @Field("Password") pass: String,
        @Field("ServiceName") service: String,
        @Field("Query") query: String,
    ): Call<ResponseHome>


    @FormUrlEncoded
    @POST("api/datasetjson")
    fun getMenu(
        @Field("Key") key: String,
        @Field("Host") host: String,
        @Field("Port") port: String,
        @Field("User") user: String,
        @Field("Password") pass: String,
        @Field("ServiceName") service: String,
        @Field("Query") query: String,
    ): Call<List<MenuResponse>>

//
//    @FormUrlEncoded
//    @POST("api/datasetjson")
//    fun getData3(
//        @Field("Key") key: String,
//        @Field("Host") host: String,
//        @Field("Port") port: String,
//        @Field("User") user: String,
//        @Field("Password") pass: String,
//        @Field("ServiceName") service: String,
//        @Field("Query") query: String,
//    ): Call<Response3>
//
//
//    @FormUrlEncoded
//    @POST("api/ExeOracle")
//    fun saveData(
//        @Field("Key") key: String,
//        @Field("Host") host: String,
//        @Field("Port") port: String,
//        @Field("User") user: String,
//        @Field("Password") pass: String,
//        @Field("ServiceName") service: String,
//        @Field("Query") query: String,
//    ): Call<Response2>

}