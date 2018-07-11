package com.hieupham.data.test.source.local

import com.hieupham.data.data.TestDataProvider.Companion.BLOCK_HEIGHT
import com.hieupham.data.data.TestDataProvider.Companion.LIMITED_RESULT
import com.hieupham.data.data.TestDataProvider.Companion.assetDatas1
import com.hieupham.data.data.TestDataProvider.Companion.blockData1
import com.hieupham.data.data.TestDataProvider.Companion.sqlException
import com.hieupham.data.data.TestDataProvider.Companion.transactionDatas1
import com.hieupham.data.data.TestDataProvider.Companion.transactionsResponse1
import com.hieupham.data.source.local.TransactionLocalDataSource
import com.hieupham.data.source.local.api.DatabaseApi
import com.hieupham.data.source.remote.api.response.TransactionsResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.InjectMocks

class TransactionLocalDataSourceTest : LocalDataSourceTest() {

    @InjectMocks
    lateinit var dataSource: TransactionLocalDataSource

    @Test
    fun testGetTransactions_BlockNumberGreaterThanMaxBlockNumber_NothingIsReturn() {
        val blockNumber = BLOCK_HEIGHT + 1
        val observer = TestObserver<TransactionsResponse>()
        whenever(
                databaseApi.transactionDao().getMaxBlockNumber()).thenReturn(
                Single.just(BLOCK_HEIGHT))
        whenever(databaseApi.single(any<(DatabaseApi) -> Single<Long>>())).thenCallRealMethod()

        dataSource.getTransactions(blockNumber, LIMITED_RESULT).subscribe(observer)

        observer.assertNoValues()
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_BlockNumberEqualMaxBlockNumber_DataIsReturn() {
        val blockNumber = BLOCK_HEIGHT
        val observer = TestObserver<TransactionsResponse>()
        val expectedResult = transactionsResponse1()
        val assetIds = transactionDatas1().map { data -> data.assetId }.toTypedArray()

        whenever(databaseApi.single(any<(DatabaseApi) -> Single<Long>>())).thenCallRealMethod()
        whenever(databaseApi.maybe(any<(DatabaseApi) -> Maybe<Any>>())).thenCallRealMethod()
        whenever(
                databaseApi.transactionDao().getMaxBlockNumber()).thenReturn(
                Single.just(BLOCK_HEIGHT))
        whenever(databaseApi.transactionDao().get(blockNumber, LIMITED_RESULT)).thenReturn(
                Maybe.just(transactionDatas1()))
        whenever(databaseApi.assetDao().get(*assetIds)).thenReturn(Maybe.just(assetDatas1()))
        whenever(databaseApi.blockDao().get(blockNumber)).thenReturn(Maybe.just(blockData1()))

        dataSource.getTransactions(blockNumber, LIMITED_RESULT).subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_BlockNumberLessThanMaxBlockNumber_DataIsReturn() {
        val blockNumber = BLOCK_HEIGHT
        val blockHeight = BLOCK_HEIGHT + 1
        val observer = TestObserver<TransactionsResponse>()
        val expectedResult = transactionsResponse1()
        val assetIds = transactionDatas1().map { data -> data.assetId }.toTypedArray()

        whenever(databaseApi.single(any<(DatabaseApi) -> Single<Long>>())).thenCallRealMethod()
        whenever(databaseApi.maybe(any<(DatabaseApi) -> Maybe<Any>>())).thenCallRealMethod()
        whenever(
                databaseApi.transactionDao().getMaxBlockNumber()).thenReturn(
                Single.just(blockHeight))
        whenever(databaseApi.transactionDao().get(blockNumber, LIMITED_RESULT)).thenReturn(
                Maybe.just(transactionDatas1()))
        whenever(databaseApi.assetDao().get(*assetIds)).thenReturn(
                Maybe.just(assetDatas1()))
        whenever(databaseApi.blockDao().get(blockNumber)).thenReturn(Maybe.just(blockData1()))

        dataSource.getTransactions(blockNumber, LIMITED_RESULT).subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_BlockNumberLessThanMaxBlockNumberNGetTransactionError_ErrorIsThrow() {
        val blockNumber = BLOCK_HEIGHT
        val blockHeight = BLOCK_HEIGHT
        val observer = TestObserver<TransactionsResponse>()
        val expectedResult = sqlException()

        whenever(databaseApi.single(any<(DatabaseApi) -> Single<Long>>())).thenCallRealMethod()
        whenever(databaseApi.maybe(any<(DatabaseApi) -> Maybe<Any>>())).thenCallRealMethod()
        whenever(databaseApi.transactionDao().getMaxBlockNumber()).thenReturn(
                Single.just(blockHeight))
        whenever(databaseApi.transactionDao().get(blockNumber, LIMITED_RESULT)).thenReturn(
                Maybe.error(expectedResult))
        dataSource.getTransactions(blockNumber, LIMITED_RESULT).subscribe(observer)

        observer.assertError(expectedResult)
        observer.assertError(expectedResult::class.java)
        observer.assertNotComplete()
        observer.assertNoValues()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_BlockNumberLessThanMaxBlockNumberNGetAssetError_ErrorIsThrow() {
        val blockNumber = BLOCK_HEIGHT
        val blockHeight = BLOCK_HEIGHT
        val observer = TestObserver<TransactionsResponse>()
        val expectedResult = sqlException()
        val assetIds = transactionDatas1().map { data -> data.assetId }.toTypedArray()

        whenever(databaseApi.single(any<(DatabaseApi) -> Single<Long>>())).thenCallRealMethod()
        whenever(databaseApi.maybe(any<(DatabaseApi) -> Maybe<Any>>())).thenCallRealMethod()
        whenever(databaseApi.transactionDao().getMaxBlockNumber()).thenReturn(
                Single.just(blockHeight))
        whenever(databaseApi.transactionDao().get(blockNumber, LIMITED_RESULT)).thenReturn(
                Maybe.just(transactionDatas1()))
        whenever(databaseApi.assetDao().get(*assetIds)).thenReturn(
                Maybe.error(expectedResult))
        dataSource.getTransactions(blockNumber, LIMITED_RESULT).subscribe(observer)

        observer.assertError(expectedResult)
        observer.assertError(expectedResult::class.java)
        observer.assertNotComplete()
        observer.assertNoValues()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransactions_BlockNumberLessThanMaxBlockNumberNGetBlockError_ErrorIsThrow() {
        val blockNumber = BLOCK_HEIGHT
        val blockHeight = BLOCK_HEIGHT
        val observer = TestObserver<TransactionsResponse>()
        val expectedResult = sqlException()
        val assetIds = transactionDatas1().map { data -> data.assetId }.toTypedArray()

        whenever(databaseApi.single(any<(DatabaseApi) -> Single<Long>>())).thenCallRealMethod()
        whenever(databaseApi.maybe(any<(DatabaseApi) -> Maybe<Any>>())).thenCallRealMethod()
        whenever(databaseApi.transactionDao().getMaxBlockNumber()).thenReturn(
                Single.just(blockHeight))
        whenever(databaseApi.transactionDao().get(blockNumber, LIMITED_RESULT)).thenReturn(
                Maybe.just(transactionDatas1()))
        whenever(databaseApi.assetDao().get(*assetIds)).thenReturn(
                Maybe.just(assetDatas1()))
        whenever(databaseApi.blockDao().get(blockNumber)).thenReturn(Maybe.error(expectedResult))
        dataSource.getTransactions(blockNumber, LIMITED_RESULT).subscribe(observer)

        observer.assertError(expectedResult)
        observer.assertError(expectedResult::class.java)
        observer.assertNotComplete()
        observer.assertNoValues()
        observer.assertTerminated()
    }

}