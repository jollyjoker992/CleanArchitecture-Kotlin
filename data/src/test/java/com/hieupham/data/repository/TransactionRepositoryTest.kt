package com.hieupham.data.repository

import com.hieupham.data.data.*
import com.hieupham.data.source.TransactionRepositoryImpl
import com.hieupham.data.source.local.TransactionLocalDataSource
import com.hieupham.data.source.remote.Mapper
import com.hieupham.data.source.remote.api.TransactionRemoteDataSource
import com.hieupham.data.source.remote.api.response.TransactionResponse
import com.hieupham.data.source.remote.api.response.TransactionsResponse
import com.hieupham.data.utils.common.Constant
import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.entity.CompositeTransactions
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`


class TransactionRepositoryTest : RepositoryTest() {

    @Mock
    lateinit var localDataSource: TransactionLocalDataSource

    @Mock
    lateinit var remoteDataSource: TransactionRemoteDataSource

    @Mock
    lateinit var mapper: Mapper

    @InjectMocks
    lateinit var repository: TransactionRepositoryImpl

    @Test
    fun testGetTransactions_NoError_DataIsReturn() {

        val expectedResult = compositeTransactions1()
        val expectedResponse = transactionsResponse1()
        val blockNumber = blockData1().number
        `when`(remoteDataSource.getTransactions(blockNumber,
                Constant.LIMITED_RESULT)).thenReturn(
                Single.just(expectedResponse))
        `when`(localDataSource.save(expectedResponse)).thenReturn(
                Completable.complete())
        `when`(mapper.map<TransactionsResponse, CompositeTransactions>()).thenCallRealMethod()
        val streamGetTransactions = repository.getTransactions(blockNumber)

        val observer = TestObserver<CompositeTransactions>()
        streamGetTransactions.subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_NetworkError_DataIsReturn() {

        val expectedResult = compositeTransactions1()
        val expectedResponse = transactionsResponse1()
        val blockNumber = blockData1().number
        val expectedException = timeoutException()
        `when`(remoteDataSource.getTransactions(blockNumber,
                Constant.LIMITED_RESULT)).thenReturn(
                Single.error(expectedException))
        `when`(localDataSource.getTransactions(blockNumber, Constant.LIMITED_RESULT)).thenReturn(
                Maybe.just(expectedResponse))
        `when`(mapper.map<TransactionsResponse, CompositeTransactions>()).thenCallRealMethod()
        val streamGetTransactions = repository.getTransactions(blockNumber)

        val observer = TestObserver<CompositeTransactions>()
        streamGetTransactions.subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_DatabaseError_ExceptionIsThrown() {

        val blockNumber = blockData1().number
        val networkException = timeoutException()
        val expectedException = sqlException()
        `when`(remoteDataSource.getTransactions(blockNumber,
                Constant.LIMITED_RESULT)).thenReturn(
                Single.error(networkException))
        `when`(localDataSource.getTransactions(blockNumber, Constant.LIMITED_RESULT)).thenReturn(
                Maybe.error(expectedException))
        `when`(mapper.map<TransactionsResponse, CompositeTransactions>()).thenCallRealMethod()
        val streamGetTransactions = repository.getTransactions(blockNumber)

        val observer = TestObserver<CompositeTransactions>()
        streamGetTransactions.subscribe(observer)

        observer.assertError(expectedException.javaClass)
        observer.assertErrorMessage(expectedException.message)
        observer.assertNotComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_NoError_DataIsReturn() {

        val expectedResult = compositeTransaction1()
        val expectedResponse = transactionResponse1()
        val id = transaction1().id
        `when`(remoteDataSource.getTransaction(id)).thenReturn(
                Single.just(expectedResponse))
        `when`(localDataSource.save(expectedResponse)).thenReturn(
                Completable.complete())
        `when`(mapper.map<TransactionResponse, CompositeTransaction>()).thenCallRealMethod()
        val streamGetTransaction = repository.getTransaction(id)

        val observer = TestObserver<CompositeTransaction>()
        streamGetTransaction.subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_NetworkError_DataIsReturn() {

        val expectedResult = compositeTransaction1()
        val expectedResponse = transactionResponse1()
        val id = transaction1().id
        val expectedException = timeoutException()
        `when`(remoteDataSource.getTransaction(id)).thenReturn(
                Single.error(expectedException))
        `when`(localDataSource.getTransaction(id)).thenReturn(Maybe.just(expectedResponse))
        `when`(mapper.map<TransactionResponse, CompositeTransaction>()).thenCallRealMethod()
        val streamGetTransaction = repository.getTransaction(id)

        val observer = TestObserver<CompositeTransaction>()
        streamGetTransaction.subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_SQLError_ExceptionIsThrown() {
    }
}