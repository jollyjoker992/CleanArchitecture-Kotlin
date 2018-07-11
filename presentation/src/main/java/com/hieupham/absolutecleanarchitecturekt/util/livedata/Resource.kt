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

        fun <T> error(throwable: Throwable?, data: T?): Resource<T> {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Resource<*>) return false

        if (status != other.status) return false
        if (data != other.data) return false
        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + (throwable?.hashCode() ?: 0)
        return result
    }


}