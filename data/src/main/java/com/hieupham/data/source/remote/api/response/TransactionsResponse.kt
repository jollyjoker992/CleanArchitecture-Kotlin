package com.hieupham.data.source.remote.api.response

import android.support.annotation.VisibleForTesting
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
class TransactionsResponse() : Mapable<CompositeTransactions>, Response {

    @Expose
    @SerializedName("txs")
    lateinit var transactions: List<TransactionData>

    @Expose
    lateinit var assets: List<AssetData>

    @Expose
    lateinit var blocks: List<BlockData>

    @VisibleForTesting
    constructor(transactions: List<TransactionData>, assets: List<AssetData>,
            blocks: List<BlockData>) : this() {
        this.transactions = transactions
        this.assets = assets
        this.blocks = blocks
    }

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TransactionsResponse) return false

        if (transactions != other.transactions) return false
        if (assets != other.assets) return false
        if (blocks != other.blocks) return false

        return true
    }

    override fun hashCode(): Int {
        var result = transactions.hashCode()
        result = 31 * result + assets.hashCode()
        result = 31 * result + blocks.hashCode()
        return result
    }


}
