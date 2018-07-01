package com.hieupham.data.source.local

import com.hieupham.data.source.local.api.DatabaseApi
import com.hieupham.data.source.local.api.SharedPrefApi
import com.hieupham.data.source.remote.api.response.TransactionResponse
import com.hieupham.data.source.remote.api.response.TransactionsResponse
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
open class TransactionLocalDataSource @Inject constructor(databaseApi: DatabaseApi,
        sharedPrefApi: SharedPrefApi) : LocalDataSource(databaseApi, sharedPrefApi) {

    fun getTransactions(blockNumber: Long, limit: Int): Maybe<TransactionsResponse> {
        return singleDb { databaseApi -> databaseApi.transactionDao().getMaxBlockNumber() }
                .flatMapMaybe { maxBlockNumber ->
                    if (blockNumber > maxBlockNumber) Maybe.empty()
                    else maybeDb { databaseApi ->
                        databaseApi.transactionDao().get(blockNumber, limit)
                    }
                }.flatMap { transactions ->
                    val response = TransactionsResponse()
                    response.transactions = transactions
                    if (transactions.isEmpty()) Maybe.just(response)
                    else maybeDb { databaseApi ->
                        val assetIds = transactions.map { data -> data.assetId }.toTypedArray()
                        databaseApi.assetDao().get(*assetIds).map { assets ->
                            response.assets = assets
                            response
                        }
                    }
                }.flatMap { response ->
                    if (response.transactions.isEmpty()) Maybe.just(response)
                    else maybeDb { databaseApi ->
                        databaseApi.blockDao().get(blockNumber).map { block ->
                            response.blocks = listOf(block)
                            response
                        }
                    }
                }
    }

    fun getTransaction(id: String): Maybe<TransactionResponse> {
        return maybeDb { databaseApi -> databaseApi.transactionDao().get(id) }
                .flatMap { transaction ->
                    val response = TransactionResponse()
                    response.transaction = transaction
                    maybeDb { databaseApi ->
                        databaseApi.assetDao().get(transaction.assetId).map { asset ->
                            response.asset = asset
                            response
                        }
                    }
                }.flatMap { response ->
                    maybeDb { databaseApi ->
                        databaseApi.blockDao().get(response.transaction.blockNumber)
                    }
                            .map { block ->
                                response.block = block
                                response
                            }
                }
    }

    fun save(response: TransactionsResponse): Completable {
        return completableDb { databaseApi ->
            databaseApi.blockDao().save(response.blocks)
            databaseApi.assetDao().save(response.assets)
            databaseApi.transactionDao().save(response.transactions)
            Completable.complete()
        }
    }

    fun save(response: TransactionResponse): Completable {
        return completableDb { databaseApi ->
            databaseApi.blockDao().save(response.block)
            databaseApi.assetDao().save(response.asset)
            databaseApi.transactionDao().save(response.transaction)
            Completable.complete()
        }
    }

    fun saveLastKnownBlockHeight(height: Long): Completable {
        return completableSharedPref { sharedPrefApi ->
            sharedPrefApi.put<Any>(SharedPrefApi.LAST_KNOWN_BLOCK_HEIGHT, height)
        }
    }

    fun getLastKnownBlockHeight(): Single<Long> {
        return singleSharedPref { emitter, sharedPrefApi ->
            emitter.onSuccess(
                    sharedPrefApi.get(SharedPrefApi.LAST_KNOWN_BLOCK_HEIGHT, Long::class))
        }
    }


}