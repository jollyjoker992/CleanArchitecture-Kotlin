package com.hieupham.data.source.remote.api.response

import android.support.annotation.VisibleForTesting
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hieupham.data.model.AssetData
import com.hieupham.data.model.BlockData
import com.hieupham.data.model.Mapable
import com.hieupham.data.model.TransactionData
import com.hieupham.domain.entity.CompositeTransaction

/**
 * Created by hieupham on 6/26/18.
 */
class TransactionResponse() : Mapable<CompositeTransaction>, Response {

    @VisibleForTesting
    constructor(transaction: TransactionData, asset: AssetData, block: BlockData) : this() {
        this.transaction = transaction
        this.asset = asset
        this.block = block
    }

    @Expose
    @SerializedName("tx")
    lateinit var transaction: TransactionData

    @Expose
    lateinit var asset: AssetData

    @Expose
    lateinit var block: BlockData

    override fun map(): CompositeTransaction {
        return CompositeTransaction(transaction.map(), asset.map(), block.map())
    }
}



