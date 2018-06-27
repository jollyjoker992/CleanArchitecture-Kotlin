package com.hieupham.data.source.remote

import com.hieupham.data.source.local.TransactionLocalDataSource
import com.hieupham.data.source.remote.api.TransactionRemoteDataSource
import com.hieupham.data.utils.common.CommonUtils
import com.hieupham.data.utils.common.Constant
import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.entity.CompositeTransactions
import com.hieupham.domain.repository.TransactionRepository
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by hieupham on 6/26/18.
 */
class TransactionRepositoryImpl(mapper: Mapper,
        private val localDataSource: TransactionLocalDataSource,
        private val remoteDataSource: TransactionRemoteDataSource) : AbsRepository(
        mapper), TransactionRepository {

    override fun getTransaction(id: String): Maybe<CompositeTransaction> {
        return remoteDataSource.getTransaction(id).flatMapMaybe { response ->
            localDataSource.save(response).andThen(Maybe.just(response))
        }.onErrorResumeNext { throwable: Throwable ->
            if (CommonUtils.isNetworkError(throwable)) localDataSource.getTransaction(id)
            else Maybe.error(throwable)
        }.map(mapper.map())
    }

    override fun getBlockHeight(): Single<Long> {
        return remoteDataSource.getBlockHeight()
                .flatMap { height ->
                    localDataSource.saveLastKnownBlockHeight(height).andThen(Single.just(height))
                }.onErrorResumeNext { _ -> localDataSource.getLastKnownBlockHeight() }
    }

    override fun getTransactions(blockNumber: Long): Maybe<CompositeTransactions> {
        return remoteDataSource.getTransactions(blockNumber,
                Constant.LIMITED_RESULT)
                .flatMapMaybe { response ->
                    localDataSource.save(response).andThen(Maybe.just(response))
                }.onErrorResumeNext { throwable: Throwable ->
                    if (CommonUtils.isNetworkError(throwable)) localDataSource.getTransactions(
                            blockNumber, Constant.LIMITED_RESULT)
                    else Maybe.error(throwable)
                }.map(mapper.map())
    }
}