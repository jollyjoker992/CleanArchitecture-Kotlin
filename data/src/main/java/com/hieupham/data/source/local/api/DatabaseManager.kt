package com.hieupham.data.source.local.api

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.hieupham.data.model.AssetData
import com.hieupham.data.model.BlockData
import com.hieupham.data.model.TransactionData
import com.hieupham.data.source.local.api.converter.LinkedTreeMapConverter
import com.hieupham.data.source.local.api.dao.AssetDao
import com.hieupham.data.source.local.api.dao.BlockDao
import com.hieupham.data.source.local.api.dao.TransactionDao

/**
 * Created by hieupham on 6/26/18.
 */
@Database(entities = [TransactionData::class, AssetData::class, BlockData::class],
        version = 1)
@TypeConverters(LinkedTreeMapConverter::class)
abstract class DatabaseManager : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "bitmark_explorer_db"
    }

    abstract fun transactionDao(): TransactionDao

    abstract fun assetDao(): AssetDao

    abstract fun blockDao(): BlockDao
}