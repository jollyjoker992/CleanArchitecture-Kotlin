package com.hieupham.absolutecleanarchitecturekt.test.view

import android.arch.lifecycle.Observer
import android.widget.ProgressBar
import com.hieupham.absolutecleanarchitecturekt.data.compositeTransactionModelView1
import com.hieupham.absolutecleanarchitecturekt.data.timeoutException
import com.hieupham.absolutecleanarchitecturekt.feature.DialogManager
import com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail.TransactionDetailFragment
import com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail.TransactionDetailViewModel
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.util.livedata.Resource
import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy

@Suppress("UNCHECKED_CAST")
class TransactionDetailViewTest : ViewTest() {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var viewModel: TransactionDetailViewModel

    @Mock
    lateinit var progressBar: ProgressBar

    @Mock
    lateinit var dialogManager : DialogManager

    @InjectMocks
    @Spy
    lateinit var view: TransactionDetailFragment

    @Test
    fun testGetTransactionDetail_GetTransactionDetailSuccess_BindData() {
        val response = compositeTransactionModelView1()
        val expectedResult = Resource.success(response)

        whenever(viewModel.liveTransactionDetail().observe(any(), any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as Observer<Resource<CompositeTransactionModelView>>
            observer.onChanged(expectedResult)
        }
        view.observe()

        verify(view).bindData(response)
        verify(view, never()).showError(any())
        verify(view, never()).showProgress(true)
    }

    @Test
    fun testGetTransactionDetail_GetTransactionDetailFailed_ShowErrorInDialog() {
        val expectedResult = Resource.error<CompositeTransactionModelView>(timeoutException(), null)

        whenever(viewModel.liveTransactionDetail().observe(any(), any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as Observer<Resource<CompositeTransactionModelView>>
            observer.onChanged(expectedResult)
        }
        view.observe()

        verify(view, never()).bindData(any())
        verify(view).showError(expectedResult.throwable!!)
        verify(view, never()).showProgress(true)
    }

    @Test
    fun testGetTransactionDetail_AnyResult_DisplayNHideProgressBar() {
        val response = compositeTransactionModelView1()
        val loadingData = Resource.loading<CompositeTransactionModelView>(null)
        val expectedResult = Resource.success(response)

        whenever(viewModel.liveTransactionDetail().observe(any(), any())).thenAnswer { invocation ->
            val observer = invocation.arguments[1] as Observer<Resource<CompositeTransactionModelView>>
            observer.onChanged(loadingData)
            observer.onChanged(expectedResult)
        }
        view.observe()

        verify(view).showProgress(true)
        verify(view, times(2)).showProgress(false)
    }

}