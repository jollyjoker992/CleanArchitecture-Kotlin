package com.hieupham.data.source.local.api.dao

import android.arch.persistence.room.*
import com.hieupham.data.model.TransactionData
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by hieupham on 6/26/18.
 */
@Dao
abstract class TransactionDao {

    @Query("SELECT * FROM `Transaction` ORDER BY block_number ASC")
    abstract fun get(): Maybe<List<TransactionData>>

    @Query("SELECT * FROM (SELECT * FROM `Transaction` ORDER BY block_number ASC) WHERE block_number = :block_number LIMIT :limit")
    abstract fun get(block_number: Long, limit: Int): Maybe<List<TransactionData>>

    @Query("SELECT COALESCE(MAX(block_number), 0) FROM `Transaction`")
    abstract fun getMaxBlockNumber(): Single<Long>

    @Query("SELECT * FROM `Transaction` WHERE id = :id")
    abstract fun get(id: String): Maybe<TransactionData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(transactions: List<TransactionData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(transaction: TransactionData)

    @Query("DELETE FROM `Transaction`")
    abstract fun delete()

    @Delete
    abstract fun delete(transaction: TransactionData)
}