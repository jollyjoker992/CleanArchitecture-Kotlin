package com.hieupham.data.source.remote.api.middleware

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by hieupham on 6/26/18.
 */
class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type?, annotations: Array<out Annotation>?,
            retrofit: Retrofit?): CallAdapter<*, *>? {
        return null
    }
}