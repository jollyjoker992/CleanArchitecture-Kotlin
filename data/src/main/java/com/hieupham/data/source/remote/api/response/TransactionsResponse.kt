package com.hieupham.data.source.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hieupham.data.model.AssetData
import com.hieupham.data.model.BlockData
import com.hieupham.data.model.Mapable
import com.hieupham.data.model.TransactionData
import com.hieupham.domain.entity.Asset
import com.hieupham.domain.entity.Block
import com.hieupham.domain.entity.CompositeTransactions
import com.hieupham.domain.entity.Transaction

/**
 * Created by hieupham on 6/26/18.
 */
class TransactionsResponse : Mapable<CompositeTransactions>, Response {

    @Expose
    @SerializedName("txs")
    lateinit var transactions: List<TransactionData>

    @Expose
    lateinit var assets: List<AssetData>

    @Expose
    lateinit var blocks: List<BlockData>

    override fun map(): CompositeTransactions {
        val transactions = mutableListOf<Transaction>()
        val assets = mutableListOf<Asset>()
        val blocks = mutableListOf<Block>()
        this.transactions.forEach { transaction: TransactionData ->
            transactions.add(transaction.map())
        }

        this.assets.forEach { asset: AssetData ->
            assets.add(asset.map())
        }

        this.blocks.forEach { block: BlockData ->
            blocks.add(block.map())
        }

        return CompositeTransactions(transactions, assets, blocks)
    }
}
