package com.hieupham.domain.entity

/**
 * Created by hieupham on 6/26/18.
 */
data class CompositeTransactions(var transactions: List<Transaction>,
        var assets: List<Asset>, var blocks: List<Block>) : Entity
