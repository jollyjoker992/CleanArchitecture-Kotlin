package com.hieupham.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hieupham.data.source.remote.api.middleware.BitmarkApiInterceptor
import com.hieupham.data.source.remote.api.service.BitmarkApi
import com.hieupham.data.source.remote.api.service.ServiceGenerator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by hieupham on 6/27/18.
 */
@Module
class NetworkModule {

    companion object {
        const val API_ENDPOINT = "https://api.test.bitmark.com/v1/"
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(context: Context): BitmarkApiInterceptor {
        return BitmarkApiInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideBitmarkApi(gson: Gson, interceptor: BitmarkApiInterceptor): BitmarkApi {
        return ServiceGenerator.createService(API_ENDPOINT, BitmarkApi::class.java, gson,
                interceptor)
    }
}