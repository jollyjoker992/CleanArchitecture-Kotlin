package com.hieupham.data.source.remote.api.service

import com.hieupham.data.source.remote.api.response.InfoResponse
import com.hieupham.data.source.remote.api.response.TransactionResponse
import com.hieupham.data.source.remote.api.response.TransactionsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by hieupham on 6/26/18.
 */
interface BitmarkApi {

    @GET("txs")
    fun getTransactions(@Query("block_number") blockNumber: Long,
            @Query("to") to: String, @Query("limit") limit: Int, @Query("asset") asset: Boolean,
            @Query("block") block: Boolean): Single<TransactionsResponse>

    @GET("txs/{id}")
    fun getTransaction(@Path("id") id: String, @Query("asset") asset: Boolean,
            @Query("block") block: Boolean): Single<TransactionResponse>

    @GET("info")
    fun getInfo(): Single<InfoResponse>
}