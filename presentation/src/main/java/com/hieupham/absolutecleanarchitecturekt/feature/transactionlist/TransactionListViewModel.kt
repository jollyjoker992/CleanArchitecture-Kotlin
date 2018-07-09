package com.hieupham.absolutecleanarchitecturekt.feature.transactionlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.VisibleForTesting
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.mapper.CompositeTransactionModelViewMapper
import com.hieupham.absolutecleanarchitecturekt.util.common.Duration
import com.hieupham.absolutecleanarchitecturekt.util.common.IntervalScheduler
import com.hieupham.absolutecleanarchitecturekt.util.livedata.LiveDataObserver
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.domain.entity.CompositeTransactions
import com.hieupham.domain.interactor.Observer
import com.hieupham.domain.interactor.UseCase
import com.hieupham.domain.interactor.usecase.GetTransactionsUseCase
import java.util.concurrent.TimeUnit

open class TransactionListViewModel(getTransactionsUseCase: GetTransactionsUseCase,
        private val taskScheduler: IntervalScheduler,
        private val mapper: CompositeTransactionModelViewMapper) : ViewModel(
        getTransactionsUseCase) {

    private val liveNextTransactions = MutableLiveData<Resource<List<CompositeTransactionModelView>>>()
    private val liveLatestTransactions = MutableLiveData<Resource<List<CompositeTransactionModelView>>>()
    private val liveRefreshTransactions = MutableLiveData<Resource<List<CompositeTransactionModelView>>>()

    override fun onCreate() {
        super.onCreate()
        taskScheduler.observe(this)
        val duration = Duration.from(2, TimeUnit.MINUTES)
        taskScheduler.triggerOnMain().delay(duration).period(duration).schedule()
    }

    override fun onDestroy() {
        taskScheduler.cancel()
        super.onDestroy()
    }

    override fun nextTransactions(): LiveData<Resource<List<CompositeTransactionModelView>>> {
        return liveNextTransactions
    }

    override fun latestTransactions(): LiveData<Resource<List<CompositeTransactionModelView>>> {
        return liveLatestTransactions
    }

    override fun refreshedTransactions(): LiveData<Resource<List<CompositeTransactionModelView>>> {
        return liveRefreshTransactions
    }

    override fun getNextTransactions() {
        val output = LiveDataObserver.from<CompositeTransactions, List<CompositeTransactionModelView>>(
                liveNextTransactions) { transactions -> mapper.transform(transactions) }
        getNextTransactions(output)
    }

    override fun refreshTransactions() {
        val output = LiveDataObserver.from<CompositeTransactions, List<CompositeTransactionModelView>>(
                liveRefreshTransactions) { transactions -> mapper.transform(transactions) }
        refreshTransactions(output)
    }

    override fun fetchLatestTransactions() {
        val output = LiveDataObserver.from<CompositeTransactions, List<CompositeTransactionModelView>>(
                liveLatestTransactions) { transitions -> mapper.transform(transitions) }
        fetchLatestTransactions(output)
    }

    override fun onSchedule() {
        fetchLatestTransactions()
    }

    @VisibleForTesting
    fun getNextTransactions(observer: Observer<CompositeTransactions>) {
        getTransactionsUseCase.next().execute(UseCase.EmptyInput.instance(), observer)
    }

    @VisibleForTesting
    fun refreshTransactions(observer: Observer<CompositeTransactions>) {
        getTransactionsUseCase.refresh().execute(UseCase.EmptyInput.instance(), observer)
    }

    @VisibleForTesting
    fun fetchLatestTransactions(observer: Observer<CompositeTransactions>) {
        getTransactionsUseCase.fetchLatest().execute(UseCase.EmptyInput.instance(), observer)
    }

}