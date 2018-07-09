package com.hieupham.data.source.remote

import com.hieupham.data.ConnectionTimeout
import com.hieupham.data.Endpoint
import com.hieupham.data.NetworkModule
import com.hieupham.data.source.remote.api.RemoteDataSource
import com.hieupham.data.source.remote.api.TransactionRemoteDataSource
import com.hieupham.data.source.remote.api.converter.Converter
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
abstract class RemoteDataSourceTest<T : RemoteDataSource> {

    @Rule
    @JvmField
    val globalTimeoutRule: TestRule = Timeout.seconds(20)

    protected lateinit var dataSource: T

    protected val mockServer = MockWebServer()

    @Before
    open fun before() {
        val networkModule = NetworkModule()
        val sourceClassType = getGenericTypeClass<RemoteDataSourceTest<T>, T>(this)
        val service = networkModule.provideBitmarkApi(
                Endpoint(mockServer.url("/").toString()),
                ConnectionTimeout(ServiceGenerator.TEST_CONNECTION_TIMEOUT),
                networkModule.provideGson(),
                networkModule.provideAuthInterceptor())
        @Suppress("UNCHECKED_CAST")
        dataSource = when {
            sourceClassType.isAssignableFrom(
                    TransactionRemoteDataSource::class.java) -> TransactionRemoteDataSource(service,
                    Converter()) as T
            else -> throw UnsupportedOperationException()
        }
    }

    @After
    open fun after() {
        mockServer.shutdown()
    }
}