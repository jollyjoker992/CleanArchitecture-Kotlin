package com.hieupham.data.source.remote

import com.hieupham.data.model.Mapable
import javax.inject.Inject

/**
 * Created by hieupham on 6/26/18.
 */
open class Mapper @Inject constructor() {

    open fun <T : Mapable<R>, R> map(): (T) -> R = { t -> t.map() }
}