package com.hieupham.domain.usecase

import com.hieupham.domain.data.TRANSACTION_ID
import com.hieupham.domain.data.compositeTransaction1
import com.hieupham.domain.data.timeoutException
import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.interactor.usecase.GetTransactionUseCase
import com.hieupham.domain.repository.TransactionRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

open class GetTransactionUseCaseTest : UseCaseTest() {

    @Mock
    lateinit var transactionRepo: TransactionRepository

    @InjectMocks
    lateinit var usecase: GetTransactionUseCase

    @Test
    fun testGetTransaction_NoError_DataIsReturn() {
        val expectedResult = compositeTransaction1()
        val observer = TestObserver<CompositeTransaction>()
        val input = GetTransactionUseCase.Input.from(TRANSACTION_ID)
        whenever(transactionRepo.getTransaction(any())).thenReturn(Maybe.just(expectedResult))

        usecase.dataStream(input).subscribe(observer)

        observer.assertValue(expectedResult)
        observer.assertNoErrors()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_InvalidTransactionId_NothingReturn() {
        val observer = TestObserver<CompositeTransaction>()
        val input = GetTransactionUseCase.Input.from("")

        usecase.dataStream(input).subscribe(observer)

        observer.assertNoErrors()
        observer.assertNoValues()
        observer.assertComplete()
        observer.assertTerminated()
    }

    @Test
    fun testGetTransaction_GetTransactionFailed_ErrorIsThrow() {
        val observer = TestObserver<CompositeTransaction>()
        val input = GetTransactionUseCase.Input.from(TRANSACTION_ID)
        val expectedResult = timeoutException()
        whenever(transactionRepo.getTransaction(any())).thenReturn(Maybe.error(expectedResult))

        usecase.dataStream(input).subscribe(observer)

        observer.assertError(expectedResult)
        observer.assertNoValues()
        observer.assertNotComplete()
        observer.assertTerminated()
    }

}