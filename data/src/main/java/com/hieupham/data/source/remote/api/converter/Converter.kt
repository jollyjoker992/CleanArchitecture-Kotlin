package com.hieupham.data.source.remote.api.converter

import com.hieupham.data.source.remote.api.response.InfoResponse
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
class Converter @Inject constructor() {

    val toInfo: (InfoResponse) -> Long = { response -> response.height }
}