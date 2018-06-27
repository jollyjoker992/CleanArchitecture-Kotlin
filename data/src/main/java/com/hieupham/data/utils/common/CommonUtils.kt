package com.hieupham.data.utils.common

import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by hieupham on 6/26/18.
 */
class CommonUtils {

    companion object {
        fun isNetworkError(throwable: Throwable): Boolean {
            return throwable is SocketTimeoutException || throwable is UnknownHostException
        }
    }
}