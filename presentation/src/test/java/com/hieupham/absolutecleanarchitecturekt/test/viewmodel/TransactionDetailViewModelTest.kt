package com.hieupham.absolutecleanarchitecturekt.test.viewmodel

import android.arch.lifecycle.Observer
import com.hieupham.absolutecleanarchitecturekt.data.TRANSACTION_ID
import com.hieupham.absolutecleanarchitecturekt.data.compositeTransaction1
import com.hieupham.absolutecleanarchitecturekt.data.compositeTransactionModelView1
import com.hieupham.absolutecleanarchitecturekt.data.timeoutException
import com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail.TransactionDetailViewModel
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.TransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.mapper.CompositeTransactionModelViewMapper
import com.hieupham.absolutecleanarchitecturekt.util.livedata.LiveDataObserver
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.entity.CompositeTransactions
import com.hieupham.domain.interactor.usecase.GetTransactionUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import java.net.SocketTimeoutException

@Suppress("UNCHECKED_CAST")
class TransactionDetailViewModelTest : ViewModelTest() {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var getTransactionUseCase: GetTransactionUseCase

    @Mock
    lateinit var mapper: CompositeTransactionModelViewMapper

    @InjectMocks
    lateinit var viewModel: TransactionDetailViewModel

    @Mock
    lateinit var observer: Observer<Resource<CompositeTransactionModelView>>

    @Test
    fun testGetTransactionDetail_GetTransactionDetailSuccess_ExactDataIsReturn() {
        val response = compositeTransaction1()
        val expectedResult = Resource.success(compositeTransactionModelView1())
        whenever(getTransactionUseCase.execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransaction, TransactionModelView>
            observer.onSuccess(response)
        }
        whenever(mapper.transform(any<CompositeTransaction>())).thenCallRealMethod()
        viewModel.liveTransactionDetail().observeForever(observer)

        viewModel.getTransactionDetail(TRANSACTION_ID)

        verify(observer).onChanged(expectedResult)
        Assert.assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.liveTransactionDetail().value!!.isSuccessful())
        Assert.assertEquals(expectedResult, viewModel.liveTransactionDetail().value)
    }

    @Test
    fun testGetTransactionDetail_GetTransactionDetailFailed_ErrorIsThrow() {
        val error = timeoutException()
        whenever(getTransactionUseCase.execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransactions, TransactionModelView>
            observer.onError(error)
        }
        viewModel.liveTransactionDetail().observeForever(observer)

        viewModel.getTransactionDetail(TRANSACTION_ID)

        Assert.assertTrue("Get transactions successfully but status in Resource wrapper is wrong",
                viewModel.liveTransactionDetail().value!!.isError())
        Assert.assertTrue(
                viewModel.liveTransactionDetail().value?.throwable is SocketTimeoutException)
        Assert.assertTrue(
                viewModel.liveTransactionDetail().value?.throwable?.message!!.contains("timeout"))
    }

    @Test
    fun testGetTransactionDetail_AnyResult_DataStateIsCorrect() {
        val loadingState = Resource.loading<CompositeTransactionModelView>(null)
        val completedState = Resource.success<CompositeTransactionModelView>(null)
        whenever(getTransactionUseCase.execute(any(),
                any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as LiveDataObserver<CompositeTransaction, TransactionModelView>
            observer.onSubscribed()
            observer.onCompleted()
        }
        viewModel.liveTransactionDetail().observeForever(observer)

        viewModel.getTransactionDetail(TRANSACTION_ID)

        verify(observer).onChanged(loadingState)
        verify(observer).onChanged(completedState)
    }


}