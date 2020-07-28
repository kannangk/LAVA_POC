package io.aequalis.lava.api

import io.aequalis.lava.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

object SearchApiClient {
    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface? {

        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.SEARCH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java
        )
        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ServicesApiInterface {

        //POST
        @Headers("Content-Type: application/json")
        @POST("/api/v3/title-views/search/0/25/titleId/asc")
        fun getSearchData(@Body body: RequestBody,@HeaderMap headers: Map<String, String>): Call<ResponseBody>

    }
}