package com.hieupham.data.source.remote

import com.hieupham.data.model.Mapable
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
class Mapper @Inject constructor() {

    fun <T : Mapable<R>, R> map(): (T) -> R = { t -> t.map() }
}