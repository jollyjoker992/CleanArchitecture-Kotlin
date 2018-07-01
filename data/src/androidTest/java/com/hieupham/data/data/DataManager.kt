package com.hieupham.data.data

import android.arch.persistence.room.Room
import android.content.Context
import com.hieupham.data.source.local.api.DatabaseManager


class DataManager(context: Context) {

    val database = Room.inMemoryDatabaseBuilder(context,
            DatabaseManager::class.java)
            .allowMainThreadQueries()
            .build()

    fun setup() {

    }

    fun teardown() {
        database.clearAllTables()
        database.close()
    }
}