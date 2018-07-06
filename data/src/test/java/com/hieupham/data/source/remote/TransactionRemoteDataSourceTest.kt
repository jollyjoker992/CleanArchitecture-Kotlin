package com.hieupham.data.source.remote

import com.hieupham.data.data.TestDataProvider.Companion.BLOCK_HEIGHT
import com.hieupham.data.data.TestDataProvider.Companion.BLOCK_NUMBER
import com.hieupham.data.data.TestDataProvider.Companion.LIMITED_RESULT
import com.hieupham.data.data.TestDataProvider.Companion.TRANSACTION_ID
import com.hieupham.data.data.TestDataProvider.Companion.timeoutException
import com.hieupham.data.data.TestDataProvider.Companion.transactionResponse1
import com.hieupham.data.data.TestDataProvider.Companion.transactionsResponse1
import com.hieupham.data.source.remote.api.TransactionRemoteDataSource
import com.hieupham.data.source.remote.api.response.TransactionResponse
import com.hieupham.data.source.remote.api.response.TransactionsResponse
import com.hieupham.data.source.remote.api.service.ServiceGenerator
import com.hieupham.data.util.TestUtil.Companion.getResponse
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Test
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class TransactionRemoteDataSourceTest : RemoteDataSourceTest<TransactionRemoteDataSource>() {

    @Test
    fun testGetTransactions_NoError_DataIsReturn() {
        val rawResponse = getResponse("transactions.json")
        val response = MockResponse().setResponseCode(200).setBody(rawResponse)
        val expectedResult = transactionsResponse1()
        val observer = TestObserver<TransactionsResponse>()
        mockServer.enqueue(response)

        dataSource.getTransactions(BLOCK_NUMBER, LIMITED_RESULT).subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertNoErrors()
        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_NetworkError_ErrorIsThrow() {
        val rawResponse = getResponse("transactions.json")
        val response = MockResponse().setResponseCode(200).setSocketPolicy(
                SocketPolicy.NO_RESPONSE).throttleBody(1024, 1,
                TimeUnit.SECONDS).setBody(rawResponse)
        val expectedResult = timeoutException()
        val observer = TestObserver<TransactionsResponse>()
        mockServer.enqueue(response)

        dataSource.getTransactions(BLOCK_NUMBER, LIMITED_RESULT).subscribe(observer)
        observer.awaitTerminalEvent(ServiceGenerator.TEST_CONNECTION_TIMEOUT * 2, TimeUnit.SECONDS)

        observer.assertNoValues()
        observer.assertError(expectedResult::class.java)
        observer.assertErrorMessage("timeout")
        observer.assertNotComplete()
        observer.assertTerminated()

    }

    @Test
    fun testGetTransactions_ServerError_ErrorIsThrow() {
        val response = MockResponse().setResponseCode(500)
        val observer = TestObserver<TransactionsResponse>()
        mockServer.enqueue(response)

        dataSource.getTransactions(BLOCK_NUMBER, LIMITED_RESULT).subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertNoValues()
        observer.assertError(HttpException::class.java)
        assertEquals(1, observer.errorCount())
        observer.assertTerminated()

    }

    @Test
    fun testGetTransaction_NoError_DataIsReturn() {
        val rawResponse = getResponse("transaction.json")
        val response = MockResponse().setResponseCode(200).setBody(rawResponse)
        val expectedResult = transactionResponse1()
        val observer = TestObserver<TransactionResponse>()
        mockServer.enqueue(response)

        dataSource.getTransaction(TRANSACTION_ID).subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertNoErrors()
        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_NetworkError_ErrorIsReturn() {
        val rawResponse = getResponse("transaction.json")
        val response = MockResponse().setResponseCode(200).setSocketPolicy(
                SocketPolicy.NO_RESPONSE).throttleBody(1024, 1,
                TimeUnit.SECONDS).setBody(rawResponse)
        val expectedResult = timeoutException()
        val observer = TestObserver<TransactionResponse>()
        mockServer.enqueue(response)

        dataSource.getTransaction(TRANSACTION_ID).subscribe(observer)
        observer.awaitTerminalEvent(ServiceGenerator.TEST_CONNECTION_TIMEOUT * 2, TimeUnit.SECONDS)

        observer.assertNoValues()
        observer.assertError(expectedResult::class.java)
        observer.assertErrorMessage("timeout")
        observer.assertNotComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_ServerError_ErrorIsReturn() {
        val response = MockResponse().setResponseCode(500)
        val observer = TestObserver<TransactionResponse>()
        mockServer.enqueue(response)

        dataSource.getTransaction(TRANSACTION_ID).subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertNoValues()
        observer.assertError(HttpException::class.java)
        assertEquals(1, observer.errorCount())
        observer.assertTerminated()
    }

    @Test
    fun testGetBlockInfo_NoError_DataIsReturn() {
        val rawResponse = getResponse("block_info.json")
        val response = MockResponse().setResponseCode(200).setBody(rawResponse)
        val observer = TestObserver<Long>()
        mockServer.enqueue(response)

        dataSource.getBlockHeight().subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertValue(BLOCK_HEIGHT)
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertTerminated()

    }

    @Test
    fun testGetBlockInfo_NetworkError_ErrorIsThrow() {
        val rawResponse = getResponse("block_info.json")
        val response = MockResponse().setResponseCode(200).setSocketPolicy(
                SocketPolicy.NO_RESPONSE).throttleBody(1024, 1,
                TimeUnit.SECONDS).setBody(rawResponse)
        val expectedResult = timeoutException()
        val observer = TestObserver<Long>()
        mockServer.enqueue(response)

        dataSource.getBlockHeight().subscribe(observer)
        observer.awaitTerminalEvent(ServiceGenerator.TEST_CONNECTION_TIMEOUT * 2, TimeUnit.SECONDS)

        observer.assertNoValues()
        observer.assertError(expectedResult::class.java)
        observer.assertErrorMessage("timeout")
        observer.assertNotComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetBlockInfo_ServerError_ErrorIsReturn() {
        val response = MockResponse().setResponseCode(500)
        val observer = TestObserver<Long>()
        mockServer.enqueue(response)

        dataSource.getBlockHeight().subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertNoValues()
        observer.assertError(HttpException::class.java)
        assertEquals(1, observer.errorCount())
        observer.assertTerminated()
    }

}