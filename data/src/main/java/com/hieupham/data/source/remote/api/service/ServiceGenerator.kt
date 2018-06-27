package com.hieupham.data.source.remote.api.service

import com.google.gson.Gson
import com.hieupham.data.BuildConfig
import com.hieupham.data.source.remote.api.middleware.RxErrorHandlingCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by hieupham on 6/26/18.
 */
class ServiceGenerator {

    companion object {

        private const val CONNECTION_TIMEOUT = 15L

        fun <T> createService(endPoint: String, serviceClass: Class<T>, gson: Gson,
                interceptor: Interceptor?): T {
            val httpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClientBuilder.addInterceptor(loggingInterceptor)
            }
            if (interceptor != null) {
                httpClientBuilder.addInterceptor(interceptor)
            }
            httpClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            val builder = Retrofit.Builder().baseUrl(endPoint)
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
            val retrofit = builder.client(httpClientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(serviceClass)
        }
    }
}