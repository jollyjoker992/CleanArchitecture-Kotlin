package com.hieupham.absolutecleanarchitecturekt.viewmodel

import android.arch.lifecycle.Observer
import com.hieupham.absolutecleanarchitecturekt.data.compositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.data.compositeTransactions1
import com.hieupham.absolutecleanarchitecturekt.feature.transactionlist.TransactionListViewModel
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.mapper.CompositeTransactionModelViewMapper
import com.hieupham.absolutecleanarchitecturekt.util.common.IntervalScheduler
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.domain.interactor.usecase.GetTransactionsUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito

class TransactionListViewModelTest : ViewModelTest() {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @Mock
    lateinit var mapper: CompositeTransactionModelViewMapper

    @Mock
    lateinit var taskScheduler: IntervalScheduler

    @InjectMocks
    lateinit var viewModel: TransactionListViewModel

    @Mock
    lateinit var observer: Observer<Resource<List<CompositeTransactionModelView>>>

    override fun before() {
        super.before()
        getTransactionsUseCase.compositeDisposable(Mockito.mock(CompositeDisposable::class.java))
    }

    @Test
    fun testNextTransactions_NoError_DataIsReturn() {
        val response = compositeTransactions1()
        val expectedResult = Resource.success(compositeTransactionModelView())
        whenever(getTransactionsUseCase.buildDataStream(any())).thenReturn(Maybe.just(response))
        viewModel.nextTransactions().observeForever(observer)


        viewModel.getNextTransactions()

        verify(observer).onChanged(expectedResult)
        assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.nextTransactions().value!!.isSuccessful())

    }


}