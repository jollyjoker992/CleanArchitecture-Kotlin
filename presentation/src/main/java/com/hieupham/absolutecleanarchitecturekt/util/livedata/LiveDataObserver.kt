package com.hieupham.absolutecleanarchitecturekt.util.livedata

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import android.os.Looper
import com.hieupham.domain.interactor.Observer

class LiveDataObserver<T, R> private constructor(private var liveData: MutableLiveData<Resource<R>>,
        private var function: (T) -> R) : Observer<T>() {

    private val handler: Handler = Handler(Looper.getMainLooper())

    companion object {
        fun <T, R> from(liveData: MutableLiveData<Resource<R>>,
                function: (T) -> R): LiveDataObserver<T, R> {
            return LiveDataObserver(liveData, function)
        }
    }

    override fun onSuccess(data: T) {
        super.onSuccess(data)
        switchOnMain { liveData.value = Resource.success(function.invoke(data)) }
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        switchOnMain { liveData.value = Resource.error(throwable, null) }
    }

    override fun onSubscribed() {
        super.onSubscribed()
        switchOnMain { liveData.value = Resource.loading(null) }
    }

    override fun onCompleted() {
        super.onCompleted()
        switchOnMain { liveData.value = Resource.success(null) }
    }

    private fun switchOnMain(action: () -> Unit) {
        handler.post { action.invoke() }
    }

}