package com.hieupham.domain.interactor.usecase

import android.support.annotation.VisibleForTesting
import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.interactor.MaybeUseCase
import com.hieupham.domain.interactor.UseCase
import com.hieupham.domain.repository.TransactionRepository
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
class GetTransactionUseCase @Inject constructor(
        private val transactionRepo: TransactionRepository) : MaybeUseCase<GetTransactionUseCase.Input, CompositeTransaction>() {

    override fun buildDataStream(input: Input): Maybe<CompositeTransaction> {
        return if (input.id.isEmpty()) Maybe.empty() else transactionRepo.getTransaction(input.id)
    }


    @VisibleForTesting
    fun dataStream(input: Input): Maybe<CompositeTransaction> {
        return buildDataStream(input)
    }


    class Input private constructor(internal val id: String) : UseCase.Input() {

        companion object {

            fun from(id: String): Input {
                return Input(id)
            }
        }
    }
}