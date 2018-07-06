package com.hieupham.data.source.local.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.hieupham.data.source.local.api.DatabaseManager
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.junit.rules.Timeout
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
abstract class DaoTest {

    @Rule
    @JvmField
    val globalTimeoutRule = Timeout.seconds(120)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val exceptionRule = ExpectedException.none()

    protected var database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getTargetContext(),
            DatabaseManager::class.java)
            .allowMainThreadQueries()
            .build()

    @Before
    open fun before() {

    }

    @After
    open fun after() {
        database.clearAllTables()
        database.close()
    }

}