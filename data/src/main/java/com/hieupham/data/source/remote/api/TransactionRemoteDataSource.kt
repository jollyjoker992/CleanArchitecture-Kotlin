package com.hieupham.data.source.remote.api

import com.hieupham.data.source.remote.api.converter.Converter
import com.hieupham.data.source.remote.api.response.TransactionResponse
import com.hieupham.data.source.remote.api.response.TransactionsResponse
import com.hieupham.data.source.remote.api.service.BitmarkApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
class TransactionRemoteDataSource @Inject constructor(bitmarkApi: BitmarkApi,
        converter: Converter) : RemoteDataSource(
        bitmarkApi, converter) {

    fun getTransactions(blockNumber: Long, limit: Int): Single<TransactionsResponse> {
        return bitmarkApi.getTransactions(blockNumber, "later", limit, true, true)
                .subscribeOn(Schedulers.io())
    }

    fun getTransaction(id: String): Single<TransactionResponse> {
        return bitmarkApi.getTransaction(id, true, true).subscribeOn(Schedulers.io())
    }

    fun getBlockHeight(): Single<Long> {
        return bitmarkApi.getInfo().map(converter.toInfo).subscribeOn(Schedulers.io())
    }
}