package com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail

import android.arch.lifecycle.LiveData
import com.hieupham.absolutecleanarchitecturekt.feature.BaseViewModel
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.domain.interactor.usecase.GetTransactionUseCase

abstract class ViewModel(
        protected val getTransactionUseCase: GetTransactionUseCase) : BaseViewModel() {

    internal abstract fun getTransactionDetail(id: String)

    internal abstract fun liveTransactionDetail(): LiveData<Resource<CompositeTransactionModelView>>

    override fun onDestroy() {
        super.onDestroy()
        getTransactionUseCase.dispose()
    }
}