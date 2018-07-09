package com.hieupham.domain.usecase

import com.hieupham.domain.data.BLOCK_HEIGHT
import com.hieupham.domain.data.timeoutException
import com.hieupham.domain.interactor.usecase.GetBlockHeightUseCase
import com.hieupham.domain.repository.TransactionRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import java.net.SocketTimeoutException

open class GetBlockHeightUseCaseTest : UseCaseTest() {

    @Mock
    lateinit var transactionRepo: TransactionRepository

    @InjectMocks
    lateinit var usecase: GetBlockHeightUseCase

    @Test
    fun testGetBlockHeight_NoError_DataIsReturn() {
        val expectedResult = BLOCK_HEIGHT
        val observer = TestObserver<Long>()
        whenever(transactionRepo.getBlockHeight()).thenReturn(Single.just(expectedResult))

        usecase.dataStream().subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertNoErrors()
        observer.assertTerminated()
    }

    @Test
    fun testGetBlockHeight_GetBlockHeightFailed_ErrorIsThrow() {
        val expectedResult = timeoutException()
        val observer = TestObserver<Long>()
        whenever(transactionRepo.getBlockHeight()).thenReturn(Single.error(expectedResult))

        usecase.dataStream().subscribe(observer)

        observer.assertError(expectedResult)
        observer.assertError(SocketTimeoutException::class.java)
        observer.assertNotComplete()
        observer.assertTerminated()
    }
}