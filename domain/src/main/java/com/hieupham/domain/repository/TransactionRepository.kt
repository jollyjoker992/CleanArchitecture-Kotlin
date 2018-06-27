package com.hieupham.domain.repository

import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.entity.CompositeTransactions
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by hieupham on 6/26/18.
 */
interface TransactionRepository : Repository {

    fun getTransactions(blockNumber: Long): Maybe<CompositeTransactions>

    fun getTransaction(id: String): Maybe<CompositeTransaction>

    fun getBlockHeight(): Single<Long>
}