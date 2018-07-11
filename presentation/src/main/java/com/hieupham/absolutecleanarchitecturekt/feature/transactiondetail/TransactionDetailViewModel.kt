package com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.mapper.CompositeTransactionModelViewMapper
import com.hieupham.absolutecleanarchitecturekt.util.livedata.LiveDataObserver
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.interactor.usecase.GetTransactionUseCase

class TransactionDetailViewModel(getTransactionUseCase: GetTransactionUseCase,
        private val mapper: CompositeTransactionModelViewMapper) : ViewModel(
        getTransactionUseCase) {

    private val liveTransactionDetail: MutableLiveData<Resource<CompositeTransactionModelView>> = MutableLiveData()

    override fun liveTransactionDetail(): LiveData<Resource<CompositeTransactionModelView>> {
        return liveTransactionDetail
    }

    override fun getTransactionDetail(id: String) {
        val input = GetTransactionUseCase.Input.from(id)
        val output: LiveDataObserver<CompositeTransaction, CompositeTransactionModelView> = LiveDataObserver.from(
                liveTransactionDetail) { transaction -> mapper.transform(transaction) }
        getTransactionUseCase.execute(input, output)
    }

}