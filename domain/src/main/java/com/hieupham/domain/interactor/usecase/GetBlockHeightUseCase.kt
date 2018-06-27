package com.hieupham.domain.interactor.usecase

import com.hieupham.domain.interactor.SingleUseCase
import com.hieupham.domain.interactor.UseCase
import com.hieupham.domain.repository.TransactionRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
class GetBlockHeightUseCase @Inject constructor(
        private val transactionRepo: TransactionRepository) : SingleUseCase<UseCase.EmptyInput, Long>() {


    override fun buildDataStream(input: EmptyInput): Single<Long> {
        return transactionRepo.getBlockHeight()
    }
}