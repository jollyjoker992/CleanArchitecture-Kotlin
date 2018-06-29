package com.hieupham.absolutecleanarchitecturekt.feature.transactionlist

import android.arch.lifecycle.LiveData
import com.hieupham.absolutecleanarchitecturekt.feature.BaseViewModel
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.util.common.IntervalScheduler
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.domain.interactor.usecase.GetTransactionsUseCase

abstract class ViewModel(
        protected val getTransactionsUseCase: GetTransactionsUseCase) : BaseViewModel(),
        IntervalScheduler.SchedulerListener {

    internal abstract fun nextTransactions(): LiveData<Resource<List<CompositeTransactionModelView>>>

    internal abstract fun latestTransactions(): LiveData<Resource<List<CompositeTransactionModelView>>>

    internal abstract fun refreshedTransactions(): LiveData<Resource<List<CompositeTransactionModelView>>>

    internal abstract fun getNextTransactions()

    internal abstract fun refreshTransactions()

    internal abstract fun fetchLatestTransactions()

    override fun onDestroy() {
        super.onDestroy()
        getTransactionsUseCase.dispose()
    }
}