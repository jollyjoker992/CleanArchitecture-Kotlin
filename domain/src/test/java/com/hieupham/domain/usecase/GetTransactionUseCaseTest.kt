package com.hieupham.domain.usecase

import com.hieupham.domain.interactor.usecase.GetTransactionUseCase
import com.hieupham.domain.repository.TransactionRepository
import org.mockito.InjectMocks
import org.mockito.Mock

class GetTransactionUseCaseTest : UseCaseTest() {

    @Mock
    lateinit var transactionRepo: TransactionRepository

    @InjectMocks
    lateinit var usecase: GetTransactionUseCase
}