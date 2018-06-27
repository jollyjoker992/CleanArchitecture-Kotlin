package com.hieupham.domain.entity

/**
 * Created by hieupham on 6/26/18.
 */
data class CompositeTransaction(var transaction: Transaction, var asset: Asset,
        var block: Block) : Entity


