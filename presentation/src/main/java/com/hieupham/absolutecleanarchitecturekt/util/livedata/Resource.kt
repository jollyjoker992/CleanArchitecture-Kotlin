package com.hieupham.absolutecleanarchitecturekt.util.livedata

import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource.Status.ERROR
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource.Status.LOADING
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource.Status.SUCCESS

class Resource<T> private constructor(val status: Int, val data: T?,
        val throwable: Throwable?) {

    object Status {
        const val SUCCESS = 0x00
        const val LOADING = 0x01
        const val ERROR = 0x02
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable, data: T?): Resource<T> {
            return Resource(ERROR, data, throwable)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }

    }

    fun isSuccessful(): Boolean {
        return status == SUCCESS
    }

    fun isError(): Boolean {
        return status == ERROR
    }

    fun isLoading(): Boolean {
        return status == LOADING
    }

    fun isEmpty(): Boolean {
        return isSuccessful() && data == null
    }
}