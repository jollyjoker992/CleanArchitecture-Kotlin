package com.hieupham.data.source.local

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hieupham.data.source.local.api.DatabaseApiImpl
import com.hieupham.data.source.local.api.SharedPrefApi
import com.hieupham.data.util.RxImmediateSchedulerRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(JUnit4::class)
abstract class LocalDataSourceTest {

    @Rule
    @JvmField
    val globalTimeoutRule: TestRule = Timeout.seconds(20)

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @JvmField
    @Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var databaseApi: DatabaseApiImpl

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var sharedPrefApi: SharedPrefApi

    @Before
    fun before() {

    }

    @After
    fun after() {
    }


}