package com.hieupham.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hieupham.data.source.remote.api.middleware.BitmarkApiInterceptor
import com.hieupham.data.source.remote.api.service.BitmarkApi
import com.hieupham.data.source.remote.api.service.ServiceGenerator
import dagger.Module
import dagger.Provides
import javax.inject.Inject
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
    fun provideAuthInterceptor(): BitmarkApiInterceptor {
        return BitmarkApiInterceptor()
    }

    @Singleton
    @Provides
    fun provideBitmarkApi(endpoint: Endpoint, timeout: ConnectionTimeout, gson: Gson,
            interceptor: BitmarkApiInterceptor): BitmarkApi {
        return ServiceGenerator.createService(endpoint.endpoint, BitmarkApi::class.java, gson,
                interceptor, timeout.time)
    }
}

class Endpoint @Inject constructor() {

    var endpoint: String

    init {
        endpoint = NetworkModule.API_ENDPOINT
    }

    constructor(endpoint: String) : this() {
        this.endpoint = endpoint
    }
}

class ConnectionTimeout @Inject constructor() {

    var time: Long

    init {
        time = ServiceGenerator.CONNECTION_TIMEOUT
    }

    constructor(time: Long) : this() {
        this.time = time
    }
}