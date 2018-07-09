package com.hieupham.absolutecleanarchitecturekt.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hieupham.absolutecleanarchitecturekt.util.RxImmediateSchedulerRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(JUnit4::class)
abstract class ViewModelTest {

    @JvmField
    @Rule
    val globalTimeoutRule: TestRule = Timeout.seconds(120)

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun before() {
    }

    @After
    open fun after() {
    }
}