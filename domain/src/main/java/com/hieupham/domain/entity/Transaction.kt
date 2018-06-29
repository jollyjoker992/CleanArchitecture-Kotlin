package com.hieupham.domain.entity

/**
 * Created by hieupham on 6/26/18.
 */
data class Transaction(val id: String, val owner: String, val assetId: String, val head: String,
        val status: String, val blockNumber: Long, val blockOffset: Long, val offset: Long,
        val expiredAt: String?, val payId: String, val previousId: String?,
        val bitmarkId: String) : Entity