package com.hieupham.data.source.remote.api

import com.hieupham.data.source.remote.api.converter.Converter
import com.hieupham.data.source.remote.api.service.BitmarkApi

/**
 * Created by hieupham on 6/26/18.
 */
abstract class RemoteDataSource(protected val bitmarkApi: BitmarkApi,
        protected val converter: Converter)