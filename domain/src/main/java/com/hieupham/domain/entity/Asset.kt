package com.hieupham.domain.entity

import com.google.gson.internal.LinkedTreeMap

/**
 * Created by hieupham on 6/26/18.
 */
data class Asset(val id: String, val name: String, val fingerPrint: String?,
        val metadata: LinkedTreeMap<String, String>?, val registrant: String, val status: String,
        val blockNumber: Long, val blockOffset: Long, val expiresAt: String?,
        val offset: Long) : Entity
