package com.hieupham.data.source.local.api.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hieupham.data.model.BlockData
import io.reactivex.Maybe

/**
 * Created by hieupham on 6/26/18.
 */
@Dao
abstract class BlockDao {

    @Query("SELECT * FROM Block ORDER BY number ASC")
    abstract fun get(): Maybe<List<BlockData>>

    @Query("SELECT * FROM Block WHERE number IN (:numbers) ORDER BY number ASC")
    abstract fun get(vararg numbers: Long): Maybe<List<BlockData>>

    @Query("SELECT * FROM Block WHERE number = :number")
    abstract fun get(number: Long): Maybe<BlockData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(blocks: List<BlockData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(block: BlockData)

    @Query("DELETE FROM Block")
    abstract fun delete()
}