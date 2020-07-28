package io.aequalis.lava.api

import io.aequalis.lava.utils.Agents
import io.aequalis.lava.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers

object TenantApiClient {

    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface? {

        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.TENANT_BASE_URL)
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
        //GET
        @Headers("Content-Type: application/json")
        @GET("/api/configuration/public-access/agent")
        fun getTenantDetails(@HeaderMap headers: Map<String, String>): Call<Agents>

    }
}