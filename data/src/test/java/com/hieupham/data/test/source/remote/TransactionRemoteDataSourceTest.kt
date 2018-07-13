package com.hieupham.data.test.source.remote

import com.hieupham.data.data.TestDataProvider.Companion.BLOCK_HEIGHT
import com.hieupham.data.data.TestDataProvider.Companion.BLOCK_NUMBER
import com.hieupham.data.data.TestDataProvider.Companion.LIMITED_RESULT
import com.hieupham.data.data.TestDataProvider.Companion.TRANSACTION_ID
import com.hieupham.data.data.TestDataProvider.Companion.timeoutException
import com.hieupham.data.data.TestDataProvider.Companion.transactionResponse1
import com.hieupham.data.data.TestDataProvider.Companion.transactionsResponse1
import com.hieupham.data.source.remote.api.TransactionRemoteDataSource
import com.hieupham.data.source.remote.api.converter.Converter
import com.hieupham.data.source.remote.api.response.InfoResponse
import com.hieupham.data.source.remote.api.response.TransactionResponse
import com.hieupham.data.source.remote.api.response.TransactionsResponse
import com.hieupham.data.source.remote.api.service.BitmarkApi
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock

class TransactionRemoteDataSourceTest : RemoteDataSourceTest() {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var bitmarkApi: BitmarkApi

    @Mock
    lateinit var converter: Converter

    @InjectMocks
    lateinit var dataSource: TransactionRemoteDataSource

    @Test
    fun testGetTransactions_GetTransactionsSuccess_DataIsReturn() {
        val expectedResult = transactionsResponse1()
        val observer = TestObserver<TransactionsResponse>()
        whenever(bitmarkApi.getTransactions(any(), any(), any(), any(), any())).thenReturn(
                Single.just(expectedResult))

        dataSource.getTransactions(BLOCK_NUMBER, LIMITED_RESULT).subscribe(observer)

        observer.assertNoErrors()
        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_GetTransactionsFailed_ErrorIsThrow() {
        val expectedResult = timeoutException()
        val observer = TestObserver<TransactionsResponse>()
        whenever(bitmarkApi.getTransactions(any(), any(), any(), any(), any())).thenReturn(
                Single.error(expectedResult))

        dataSource.getTransactions(BLOCK_NUMBER, LIMITED_RESULT).subscribe(observer)

        observer.assertNoValues()
        observer.assertError(expectedResult::class.java)
        observer.assertErrorMessage("timeout")
        observer.assertNotComplete()
        observer.assertTerminated()

    }

    @Test
    fun testGetTransaction_GetTransactionSuccess_DataIsReturn() {
        val expectedResult = transactionResponse1()
        val observer = TestObserver<TransactionResponse>()
        whenever(bitmarkApi.getTransaction(any(), any(), any())).thenReturn(
                Single.just(expectedResult))

        dataSource.getTransaction(TRANSACTION_ID).subscribe(observer)

        observer.assertNoErrors()
        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_GetTransactionFailed_ErrorIsReturn() {
        val expectedResult = timeoutException()
        val observer = TestObserver<TransactionResponse>()
        whenever(bitmarkApi.getTransaction(any(), any(), any())).thenReturn(
                Single.error(expectedResult))

        dataSource.getTransaction(TRANSACTION_ID).subscribe(observer)

        observer.assertNoValues()
        observer.assertError(expectedResult::class.java)
        observer.assertErrorMessage("timeout")
        observer.assertNotComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetBlockInfo_GetBlockHeightSuccess_DataIsReturn() {
        val expectedResult = BLOCK_HEIGHT
        val observer = TestObserver<Long>()
        val func : (InfoResponse) -> Long = mock()
        whenever(bitmarkApi.getInfo().map(func)).thenReturn(Single.just(expectedResult))

        dataSource.getBlockHeight().subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertTerminated()

    }

    @Test
    fun testGetBlockInfo_GetBlockHeightFailed_ErrorIsThrow() {
        val expectedResult = timeoutException()
        val observer = TestObserver<Long>()
        val mapFunc : (InfoResponse) -> Long = mock()
        whenever(bitmarkApi.getInfo().map(mapFunc)).thenReturn(
                Single.error(expectedResult))

        dataSource.getBlockHeight().subscribe(observer)

        observer.assertNoValues()
        observer.assertError(expectedResult::class.java)
        observer.assertErrorMessage("timeout")
        observer.assertNotComplete()
        observer.assertTerminated()
    }
}