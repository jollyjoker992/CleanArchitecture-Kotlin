package com.hieupham.data.source.remote.api.service

import android.support.annotation.VisibleForTesting
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

        const val CONNECTION_TIMEOUT = 15L
        @VisibleForTesting
        const val TEST_CONNECTION_TIMEOUT = 2L

        fun <T> createService(endPoint: String, serviceClass: Class<T>, gson: Gson,
                interceptor: Interceptor?): T {
            return createService(endPoint, serviceClass, gson, interceptor, CONNECTION_TIMEOUT)
        }

        fun <T> createService(endPoint: String, serviceClass: Class<T>, gson: Gson,
                interceptor: Interceptor?, timeout: Long): T {
            val httpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClientBuilder.addInterceptor(loggingInterceptor)
            }
            if (interceptor != null) {
                httpClientBuilder.addInterceptor(interceptor)
            }
            httpClientBuilder.writeTimeout(timeout, TimeUnit.SECONDS)
            httpClientBuilder.readTimeout(timeout, TimeUnit.SECONDS)
            httpClientBuilder.connectTimeout(timeout, TimeUnit.SECONDS)
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