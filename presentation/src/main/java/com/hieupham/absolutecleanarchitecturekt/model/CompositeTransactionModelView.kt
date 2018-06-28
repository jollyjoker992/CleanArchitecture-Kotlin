package com.hieupham.absolutecleanarchitecturekt.model

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import com.hieupham.absolutecleanarchitecturekt.R
import com.hieupham.absolutecleanarchitecturekt.util.common.DateTimeUtils
import com.hieupham.domain.entity.CompositeTransaction


class CompositeTransactionModelView() : Parcelable, Mapable<CompositeTransaction, CompositeTransactionModelView> {

    lateinit var transaction: TransactionModelView
    lateinit var asset: AssetModelView
    lateinit var block: BlockModelView

    constructor(transaction: TransactionModelView, asset: AssetModelView,
            block: BlockModelView) : this() {
        this.transaction = transaction
        this.asset = asset
        this.block = block
    }

    constructor(parcel: Parcel) : this() {
        transaction = parcel.readParcelable(TransactionModelView::class.java.classLoader)
        asset = parcel.readParcelable(AssetModelView::class.java.classLoader)
        block = parcel.readParcelable(BlockModelView::class.java.classLoader)
    }

    fun getShortOwner(): String {
        val owner = transaction.owner
        val length = owner.length
        return "[" + owner.substring(0, 4) + "..." + owner.substring(length - 4, length) + "]"
    }

    fun getBlockName(context: Context): String {
        val date = if (TextUtils.isEmpty(block.createdAt))
            ""
        else
            DateTimeUtils.changeFormat(block.createdAt,
                    DateTimeUtils.DATE_TIME_FORMAT_2)
        return String.format(context.getString(R.string.block_name_format),
                transaction.blockNumber, date?.toUpperCase() ?: "")
    }

    fun getShortDes(context: Context): String {
        return if (isTransfer())
            String.format(context.getString(R.string.transfer_to_format),
                    getShortOwner())
        else
            String.format(context.getString(R.string.issuance_by_format), getShortOwner())
    }

    fun getDescription(context: Context): String {
        return if (isTransfer())
            String.format(context.getString(R.string.bitmark_transfer_format),
                    getShortRegistrant(), getShortOwner())
        else
            String.format(context.getString(R.string.issued_by_format), transaction.owner)
    }

    fun getBitmarkId(context: Context): String {
        return String.format(context.getString(R.string.bitmark_id_format),
                transaction.bitmarkId)
    }

    fun getShortRegistrant(): String {
        val length = asset.registrant.length
        val registrant = asset.registrant
        return ("["
                + registrant.substring(0, 4)
                + "..."
                + registrant.substring(length - 4, length)
                + "]")
    }

    fun getMetadata(): String {
        if (asset.metadata == null) return ""
        val metadata = asset.metadata
        val result = StringBuilder()
        for (entry in metadata?.entries!!) {
            result.append(entry.key.toUpperCase())
                    .append(": ")
                    .append(entry.value)
                    .append("\n\n")
        }
        return result.toString()
    }

    fun isTransfer(): Boolean {
        return transaction.previousId != null
    }

    override fun map(data: CompositeTransaction): CompositeTransactionModelView {
        return CompositeTransactionModelView(
                TransactionModelView().map(data.transaction),
                AssetModelView().map(data.asset),
                BlockModelView().map(data.block))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(transaction, flags)
        parcel.writeParcelable(asset, flags)
        parcel.writeParcelable(block, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CompositeTransactionModelView> {
        override fun createFromParcel(parcel: Parcel): CompositeTransactionModelView {
            return CompositeTransactionModelView(parcel)
        }

        override fun newArray(size: Int): Array<CompositeTransactionModelView?> {
            return arrayOfNulls(size)
        }
    }

}