package com.hieupham.absolutecleanarchitecturekt.test.view

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
abstract class ViewTest {

    @JvmField
    @Rule
    val globalTimeoutRule: TestRule = Timeout.seconds(20)

    @JvmField
    @Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    open fun before() {
    }

    @After
    open fun after() {
    }
}