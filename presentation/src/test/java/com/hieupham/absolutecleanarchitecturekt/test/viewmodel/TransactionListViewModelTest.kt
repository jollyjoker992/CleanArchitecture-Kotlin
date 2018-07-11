package com.hieupham.absolutecleanarchitecturekt.test.viewmodel

import android.arch.lifecycle.Observer
import com.hieupham.absolutecleanarchitecturekt.data.compositeTransactionModelViews1
import com.hieupham.absolutecleanarchitecturekt.data.compositeTransactions1
import com.hieupham.absolutecleanarchitecturekt.data.timeoutException
import com.hieupham.absolutecleanarchitecturekt.feature.transactionlist.TransactionListViewModel
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.TransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.mapper.CompositeTransactionModelViewMapper
import com.hieupham.absolutecleanarchitecturekt.util.common.IntervalScheduler
import com.hieupham.absolutecleanarchitecturekt.util.livedata.LiveDataObserver
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.domain.entity.CompositeTransactions
import com.hieupham.domain.interactor.usecase.GetTransactionsUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import java.net.SocketTimeoutException

@Suppress("UNCHECKED_CAST")
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

    @Test
    fun testNextTransactions_GetTransactionsSuccess_ExactDataIsReturn() {
        val response = compositeTransactions1()
        val expectedResult = Resource.success(compositeTransactionModelViews1())
        whenever(getTransactionsUseCase.next().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onSuccess(response)
        }
        whenever(mapper.transform(any<CompositeTransactions>())).thenCallRealMethod()
        viewModel.nextTransactions().observeForever(observer)

        viewModel.getNextTransactions()

        verify(observer).onChanged(expectedResult)
        assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.nextTransactions().value!!.isSuccessful())
        assertEquals(expectedResult, viewModel.nextTransactions().value)
    }

    @Test
    fun testNextTransactions_GetTransactionsFailed_ErrorIsThrow() {
        val error = timeoutException()
        whenever(getTransactionsUseCase.next().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onError(error)
        }
        viewModel.nextTransactions().observeForever(observer)

        viewModel.getNextTransactions()

        assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.nextTransactions().value!!.isError())
        assertTrue(
                viewModel.nextTransactions().value?.throwable is SocketTimeoutException)
        assertTrue(viewModel.nextTransactions().value?.throwable?.message!!.contains("timeout"))
    }

    @Test
    fun testNextTransactions_AnyResult_DataStateIsCorrect() {
        val loadingState = Resource.loading<List<CompositeTransactionModelView>>(null)
        val completedState = Resource.success<List<CompositeTransactionModelView>>(null)
        whenever(getTransactionsUseCase.next().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onSubscribed()
            observer.onCompleted()
        }
        viewModel.nextTransactions().observeForever(observer)

        viewModel.getNextTransactions()

        verify(observer).onChanged(loadingState)
        verify(observer).onChanged(completedState)
    }

    @Test
    fun testRefreshTransactions_GetTransactionsSuccess_ExactDataIsReturn() {
        val response = compositeTransactions1()
        val expectedResult = Resource.success(compositeTransactionModelViews1())
        whenever(getTransactionsUseCase.refresh().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onSuccess(response)
        }
        whenever(mapper.transform(any<CompositeTransactions>())).thenCallRealMethod()
        viewModel.refreshedTransactions().observeForever(observer)

        viewModel.refreshTransactions()

        verify(observer).onChanged(expectedResult)
        assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.refreshedTransactions().value!!.isSuccessful())
        assertEquals(expectedResult, viewModel.refreshedTransactions().value)
    }

    @Test
    fun testRefreshTransactions_GetTransactionsFailed_ErrorIsThrow() {
        val error = timeoutException()
        whenever(getTransactionsUseCase.refresh().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onError(error)
        }
        viewModel.refreshedTransactions().observeForever(observer)

        viewModel.refreshTransactions()

        assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.refreshedTransactions().value!!.isError())
        assertTrue(
                viewModel.refreshedTransactions().value?.throwable is SocketTimeoutException)
        assertTrue(
                viewModel.refreshedTransactions().value?.throwable?.message!!.contains("timeout"))
    }

    @Test
    fun testRefreshTransactions_AnyResult_DataStateIsCorrect() {
        val loadingState = Resource.loading<List<CompositeTransactionModelView>>(null)
        val completedState = Resource.success<List<CompositeTransactionModelView>>(null)
        whenever(getTransactionsUseCase.refresh().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onSubscribed()
            observer.onCompleted()
        }
        viewModel.refreshedTransactions().observeForever(observer)

        viewModel.refreshTransactions()

        verify(observer).onChanged(loadingState)
        verify(observer).onChanged(completedState)
    }

    @Test
    fun testFetchLatestTransaction_GetTransactionsSuccess_ExactDataIsReturn() {
        val response = compositeTransactions1()
        val expectedResult = Resource.success(compositeTransactionModelViews1())
        whenever(getTransactionsUseCase.fetchLatest().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onSuccess(response)
        }
        whenever(mapper.transform(any<CompositeTransactions>())).thenCallRealMethod()
        viewModel.latestTransactions().observeForever(observer)

        viewModel.fetchLatestTransactions()

        verify(observer).onChanged(expectedResult)
        assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.latestTransactions().value!!.isSuccessful())
        assertEquals(expectedResult, viewModel.latestTransactions().value)
    }

    @Test
    fun testFetchLatestTransactions_GetTransactionsFailed_ErrorIsThrow() {
        val error = timeoutException()
        whenever(getTransactionsUseCase.fetchLatest().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onError(error)
        }
        viewModel.latestTransactions().observeForever(observer)

        viewModel.fetchLatestTransactions()

        assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.latestTransactions().value!!.isError())
        assertTrue(
                viewModel.latestTransactions().value?.throwable is SocketTimeoutException)
        assertTrue(
                viewModel.latestTransactions().value?.throwable?.message!!.contains("timeout"))
    }

    @Test
    fun testFetchLatestTransactions_AnyResult_DataStateIsCorrect() {
        val loadingState = Resource.loading<List<CompositeTransactionModelView>>(null)
        val completedState = Resource.success<List<CompositeTransactionModelView>>(null)
        whenever(getTransactionsUseCase.fetchLatest().execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, List<TransactionModelView>>
            observer.onSubscribed()
            observer.onCompleted()
        }
        viewModel.latestTransactions().observeForever(observer)

        viewModel.fetchLatestTransactions()

        verify(observer).onChanged(loadingState)
        verify(observer).onChanged(completedState)
    }


}