package com.hieupham.data.source.local.migration

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.arch.persistence.room.testing.MigrationTestHelper
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.hieupham.data.data.AndroidTestDataProvider.Companion.blockDatas2
import com.hieupham.data.model.BlockData
import com.hieupham.data.source.local.api.DatabaseManager
import com.hieupham.data.source.local.api.MigrationManager
import com.hieupham.data.util.AndroidTestUtil
import io.reactivex.observers.TestObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @Rule
    @JvmField
    var migrationTestHelper = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
            DatabaseManager::class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory())

    @Test
    fun verifyMigrationFrom1_2() {
        val blocks = blockDatas2()
        val database = migrationTestHelper.createDatabase(DatabaseManager.DATABASE_NAME, 1)
        insertBlock(blocks, database)
        database.close()

        migrationTestHelper.runMigrationsAndValidate(DatabaseManager.DATABASE_NAME, 2, true,
                MigrationManager.MIGRATION_1_2)
        val databaseManager = AndroidTestUtil.getDatabaseAfterPerformingMigrations<DatabaseManager>(
                migrationTestHelper, DatabaseManager.DATABASE_NAME, MigrationManager.MIGRATION_1_2)
        blocks.forEach { block -> block.bitmarkId = "" }
        databaseManager.blockDao().save(blocks)

        val observer = TestObserver<List<BlockData>>()
        databaseManager.blockDao().get().subscribe(observer)

        observer.assertValue { managedBlocks -> managedBlocks.containsAll(blocks) }
        observer.assertValue { managedBlocks ->
            managedBlocks.none {
                !it.bitmarkId.equals("") && it.bitmarkId == null
            }
        }
    }

    @Test
    fun verifyMigrationFrom2_3() {

    }

    @Test
    fun verifyMigrationFrom3_4() {

    }

    private fun insertBlock(blocks: List<BlockData>, database: SupportSQLiteDatabase) {
        for (block in blocks) {
            val values = ContentValues()
            values.put("number", block.number)
            values.put("hash", block.hash)
            values.put("created_at", block.createdAt)
            database.insert("Block", SQLiteDatabase.CONFLICT_REPLACE, values)
        }

    }
}