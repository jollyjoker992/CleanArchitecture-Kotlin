package com.hieupham.data.util

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.arch.persistence.room.testing.MigrationTestHelper
import android.support.test.InstrumentationRegistry
import java.lang.reflect.ParameterizedType

class AndroidTestUtil {

    companion object {
        inline fun <reified T, R> getGenericTypeClass(instance: Any): Class<R> {
            var type = instance.javaClass.genericSuperclass
            while (type !is ParameterizedType || type.rawType !== T::class.java) {
                type = if (type is ParameterizedType) {
                    (type.rawType as Class<*>).genericSuperclass
                } else {
                    (type as Class<*>).genericSuperclass
                }
            }

            return type.actualTypeArguments[0] as Class<R>
        }

        inline fun <reified T : RoomDatabase> getDatabaseAfterPerformingMigrations(
                migrationTestHelper: MigrationTestHelper,
                databaseName: String,
                vararg migrations: Migration): T {
            val roomDatabase = Room.databaseBuilder(
                    InstrumentationRegistry.getTargetContext(),
                    T::class.java,
                    databaseName).addMigrations(*migrations).build()
            migrationTestHelper.closeWhenFinished(roomDatabase)
            return roomDatabase
        }
    }
}

