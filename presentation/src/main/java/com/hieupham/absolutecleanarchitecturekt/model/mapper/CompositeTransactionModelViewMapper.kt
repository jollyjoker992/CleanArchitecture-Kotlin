package com.hieupham.absolutecleanarchitecturekt.model.mapper

import com.hieupham.absolutecleanarchitecturekt.model.AssetModelView
import com.hieupham.absolutecleanarchitecturekt.model.BlockModelView
import com.hieupham.absolutecleanarchitecturekt.model.CompositeTransactionModelView
import com.hieupham.absolutecleanarchitecturekt.model.TransactionModelView
import com.hieupham.domain.entity.CompositeTransaction
import com.hieupham.domain.entity.CompositeTransactions
import java.util.*
import javax.inject.Inject

open class CompositeTransactionModelViewMapper @Inject constructor() {

    fun transform(
            compositeTransactions: CompositeTransactions): List<CompositeTransactionModelView> {
        val transactions = compositeTransactions.transactions
        val assets = compositeTransactions.assets
        val blocks = compositeTransactions.blocks
        val result = ArrayList<CompositeTransactionModelView>()

        transactions.forEach { transaction ->
            var refAsset: AssetModelView? = null
            var refBlock: BlockModelView? = null
            assets.forEach assetForEach@{ asset ->
                if (transaction.assetId == asset.id) {
                    refAsset = AssetModelView().map(asset)
                    return@assetForEach
                }
            }
            blocks.forEach blockForEach@{ block ->
                if (transaction.blockNumber == block.number) {
                    refBlock = BlockModelView().map(block)
                    return@blockForEach
                }
            }
            result.add(CompositeTransactionModelView(TransactionModelView().map(transaction),
                    refAsset!!, refBlock!!))
        }
        return result
    }

    fun transform(compositeTransaction: CompositeTransaction): CompositeTransactionModelView {
        return CompositeTransactionModelView(
                TransactionModelView().map(compositeTransaction.transaction),
                AssetModelView().map(compositeTransaction.asset),
                BlockModelView().map(compositeTransaction.block))
    }
}