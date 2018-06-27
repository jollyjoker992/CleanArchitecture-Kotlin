package com.hieupham.data.source.local.api.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hieupham.data.model.AssetData
import io.reactivex.Maybe

/**
 * Created by hieupham on 6/26/18.
 */
@Dao
abstract class AssetDao {

    @Query("SELECT * FROM Asset ORDER BY block_number ASC")
    abstract fun get(): Maybe<List<AssetData>>

    @Query("SELECT * FROM Asset WHERE id = :id")
    abstract fun get(id: String): Maybe<AssetData>

    @Query("SELECT * FROM Asset WHERE block_number IN (:blockNumbers) ORDER BY block_number ASC")
    abstract fun get(vararg blockNumbers: Long): Maybe<List<AssetData>>

    @Query("SELECT * FROM Asset WHERE id IN (:ids) ORDER BY block_number ASC")
    abstract fun get(vararg ids: String): Maybe<List<AssetData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(assets: List<AssetData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(asset: AssetData)

    @Query("DELETE FROM Asset")
    abstract fun delete()
}