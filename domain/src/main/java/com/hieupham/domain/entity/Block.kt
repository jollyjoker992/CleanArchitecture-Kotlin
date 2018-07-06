package com.hieupham.domain.entity

/**
 * Created by hieupham on 6/26/18.
 */
data class Block(val number: Long, val hash: String, val createdAt: String,
        val bitmarkId: String? = "") : Entity