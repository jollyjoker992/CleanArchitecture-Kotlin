package com.hieupham.data.source.local.api

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

/**
 * Created by hieupham on 6/26/18.
 */
class MigrationManager {
    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Block ADD COLUMN bitmark_id TEXT")
                database.execSQL("UPDATE Block SET bitmark_id = ''")
            }

        }

    }
}
