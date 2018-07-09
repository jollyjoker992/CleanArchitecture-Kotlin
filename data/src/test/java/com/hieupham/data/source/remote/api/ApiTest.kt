package com.hieupham.data.source.remote.api

import com.hieupham.data.ConnectionTimeout
import com.hieupham.data.Endpoint
import com.hieupham.data.NetworkModule
import com.hieupham.data.source.remote.api.service.BitmarkApi
import com.hieupham.data.source.remote.api.service.ServiceGenerator
import com.hieupham.data.util.TestUtil.Companion.getGenericTypeClass
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
abstract class ApiTest<T> {

    @Rule
    @JvmField
    val globalTimeoutRule: TestRule = Timeout.seconds(20)

    protected val mockServer = MockWebServer()

    protected var service: T? = null

    @Before
    open fun before() {
        val networkModule = NetworkModule()
        val sourceClassType = getGenericTypeClass<ApiTest<T>, T>(this)
        @Suppress("UNCHECKED_CAST")
        service = when {
            sourceClassType.isAssignableFrom(
                    BitmarkApi::class.java) -> networkModule.provideBitmarkApi(
                    Endpoint(mockServer.url("/").toString()),
                    ConnectionTimeout(ServiceGenerator.TEST_CONNECTION_TIMEOUT),
                    networkModule.provideGson(),
                    networkModule.provideAuthInterceptor()) as T
            else -> throw UnsupportedOperationException()
        }
    }

    @After
    open fun after() {
        mockServer.shutdown()
    }


}