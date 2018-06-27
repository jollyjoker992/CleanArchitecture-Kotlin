package com.hieupham.domain.entity

/**
 * Created by hieupham on 6/26/18.
 */
data class CompositeTransactions(private var transactions: List<Transaction>,
        private var assets: List<Asset>, private var blocks: List<Block>) : Entity
