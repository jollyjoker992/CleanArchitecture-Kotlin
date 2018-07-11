package com.hieupham.absolutecleanarchitecturekt.util.livedata

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.os.Looper
import com.hieupham.domain.interactor.Observer
import java.lang.RuntimeException

open class LiveDataObserver<T, R> private constructor() : Observer<T>() {

    private var handler: Handler? = null
    private lateinit var liveData: MutableLiveData<Resource<R>>
    private lateinit var function: (T) -> R

    private constructor(liveData: MutableLiveData<Resource<R>>, function: (T) -> R) : this() {
        this.liveData = liveData
        this.function = function
        try {
            handler = Handler(Looper.getMainLooper())
        } catch (ignore: RuntimeException) {
        }

    }


    companion object {
        fun <T, R> from(liveData: MutableLiveData<Resource<R>>,
                function: (T) -> R): LiveDataObserver<T, R> {
            return LiveDataObserver(liveData, function)
        }
    }

    override fun onSuccess(data: T) {
        super.onSuccess(data)
        postValue { liveData.value = Resource.success(function.invoke(data)) }
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        postValue { liveData.value = Resource.error(throwable, null) }
    }

    override fun onSubscribed() {
        super.onSubscribed()
        postValue { liveData.value = Resource.loading(null) }
    }

    override fun onCompleted() {
        super.onCompleted()
        postValue { liveData.value = Resource.success(null) }
    }

    private fun postValue(action: () -> Unit) {
        handler?.post { action.invoke() } ?: action.invoke()
    }

}