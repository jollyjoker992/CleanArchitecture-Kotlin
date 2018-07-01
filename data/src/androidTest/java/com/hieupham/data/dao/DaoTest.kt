package com.hieupham.data.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.hieupham.data.data.DataManager
import com.hieupham.data.source.local.api.dao.AssetDao
import com.hieupham.data.source.local.api.dao.BlockDao
import com.hieupham.data.source.local.api.dao.TransactionDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import java.lang.reflect.ParameterizedType


@RunWith(AndroidJUnit4::class)
abstract class DaoTest<T> {

    @Rule
    val globalTimeoutRule: TestRule = Timeout.seconds(120)

    @Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    var dao: T? = null

    private var dataManager: DataManager = DataManager(InstrumentationRegistry.getTargetContext())

    init {
        val clazz = getDaoClass()
        @Suppress("UNCHECKED_CAST")
        dao = when {
            clazz.isAssignableFrom(AssetDao::class.java) -> dataManager.database.assetDao() as T?
            clazz.isAssignableFrom(BlockDao::class.java) -> dataManager.database.blockDao() as T?
            clazz.isAssignableFrom(
                    TransactionDao::class.java) -> dataManager.database.transactionDao() as T?
            else -> null
        }

    }

    @Before
    fun before() {
        dataManager.setup()
    }

    @After
    fun after() {
        dataManager.teardown()
    }

    private fun getDaoClass(): Class<T> {
        var daoType = javaClass.genericSuperclass
        while (daoType !is ParameterizedType || daoType.rawType !== DaoTest::class.java) {
            daoType = if (daoType is ParameterizedType) {
                (daoType.rawType as Class<*>).genericSuperclass
            } else {
                (daoType as Class<*>).genericSuperclass
            }
        }

        return daoType.actualTypeArguments[0] as Class<T>
    }


}