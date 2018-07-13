package com.hieupham.data.source.remote.api.converter

import com.hieupham.data.source.remote.api.response.InfoResponse
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
open class Converter @Inject constructor() {

    fun toInfo(): (InfoResponse) -> Long {
        return { response -> response.height }
    }
}