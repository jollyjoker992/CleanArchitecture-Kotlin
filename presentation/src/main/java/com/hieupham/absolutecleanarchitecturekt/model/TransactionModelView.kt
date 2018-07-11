package com.hieupham.absolutecleanarchitecturekt.model

import android.os.Parcel
import android.os.Parcelable
import com.hieupham.domain.entity.Transaction

class TransactionModelView() : Parcelable, Mapable<Transaction, TransactionModelView> {

    lateinit var id: String
    lateinit var owner: String
    lateinit var assetId: String
    lateinit var head: String
    lateinit var status: String
    var blockNumber: Long = 0
    var blockOffset: Long = 0
    var offset: Long = 0
    var expiredAt: String? = null
    lateinit var payId: String
    var previousId: String? = null
    lateinit var bitmarkId: String

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        owner = parcel.readString()
        assetId = parcel.readString()
        head = parcel.readString()
        status = parcel.readString()
        blockNumber = parcel.readLong()
        blockOffset = parcel.readLong()
        offset = parcel.readLong()
        expiredAt = parcel.readString()
        payId = parcel.readString()
        previousId = parcel.readString()
        bitmarkId = parcel.readString()
    }

    constructor(id: String, owner: String, assetId: String, head: String, status: String,
            blockNumber: Long, blockOffset: Long, offset: Long, expiredAt: String?, payId: String,
            previousId: String?, bitmarkId: String) : this() {
        this.id = id
        this.owner = owner
        this.assetId = assetId
        this.head = head
        this.status = status
        this.blockNumber = blockNumber
        this.blockOffset = blockOffset
        this.offset = offset
        this.expiredAt = expiredAt
        this.payId = payId
        this.previousId = previousId
        this.bitmarkId = bitmarkId
    }


    override fun map(data: Transaction): TransactionModelView {
        return TransactionModelView(id = data.id, owner = data.owner, assetId = data.assetId,
                head = data.head, status = data.status, blockNumber = data.blockNumber,
                blockOffset = data.blockOffset, offset = data.offset, expiredAt = data.expiredAt,
                payId = data.payId, previousId = data.previousId, bitmarkId = data.bitmarkId)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(owner)
        parcel.writeString(assetId)
        parcel.writeString(head)
        parcel.writeString(status)
        parcel.writeLong(blockNumber)
        parcel.writeLong(blockOffset)
        parcel.writeLong(offset)
        parcel.writeString(expiredAt)
        parcel.writeString(payId)
        parcel.writeString(previousId)
        parcel.writeString(bitmarkId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionModelView> {
        override fun createFromParcel(parcel: Parcel): TransactionModelView {
            return TransactionModelView(parcel)
        }

        override fun newArray(size: Int): Array<TransactionModelView?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TransactionModelView) return false

        if (id != other.id) return false
        if (owner != other.owner) return false
        if (assetId != other.assetId) return false
        if (head != other.head) return false
        if (status != other.status) return false
        if (blockNumber != other.blockNumber) return false
        if (blockOffset != other.blockOffset) return false
        if (offset != other.offset) return false
        if (expiredAt != other.expiredAt) return false
        if (payId != other.payId) return false
        if (previousId != other.previousId) return false
        if (bitmarkId != other.bitmarkId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + assetId.hashCode()
        result = 31 * result + head.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + blockNumber.hashCode()
        result = 31 * result + blockOffset.hashCode()
        result = 31 * result + offset.hashCode()
        result = 31 * result + (expiredAt?.hashCode() ?: 0)
        result = 31 * result + payId.hashCode()
        result = 31 * result + (previousId?.hashCode() ?: 0)
        result = 31 * result + bitmarkId.hashCode()
        return result
    }

}