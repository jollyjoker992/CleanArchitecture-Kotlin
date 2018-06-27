package com.hieupham.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hieupham.domain.entity.Transaction

/**
 * Created by hieupham on 6/26/18.
 */
@Entity(tableName = "Transaction")
data class TransactionData(
        @PrimaryKey
        @Expose
        val id: String,

        @Expose
        val owner: String,

        @Expose
        @ColumnInfo(name = "asset_id")
        @SerializedName("asset_id")
        val assetId: String,

        @Expose
        val head: String,

        @Expose
        val status: String,

        @Expose
        @ColumnInfo(name = "block_number")
        @SerializedName("block_number")
        val blockNumber: Long,

        @Expose
        @ColumnInfo(name = "block_offset")
        @SerializedName("block_offset")
        val blockOffset: Long,

        @Expose
        val offset: Long,

        @Expose
        @ColumnInfo(name = "expires_at")
        @SerializedName("expires_at")
        val expiredAt: String,

        @Expose
        @ColumnInfo(name = "pay_id")
        @SerializedName("pay_id")
        val payId: String,

        @Expose
        @ColumnInfo(name = "previous_id")
        @SerializedName("previous_id")
        val previousId: String,

        @Expose
        @ColumnInfo(name = "bitmark_id")
        @SerializedName("bitmark_id")
        val bitmarkId: String) : Mapable<Transaction> {

    override fun map(): Transaction {
        return Transaction(id = id, owner = owner, assetId = assetId, head = head, status = status,
                blockNumber = blockNumber, blockOffset = blockOffset, offset = offset,
                expiredAt = expiredAt, payId = payId, previousId = previousId,
                bitmarkId = bitmarkId)
    }
}