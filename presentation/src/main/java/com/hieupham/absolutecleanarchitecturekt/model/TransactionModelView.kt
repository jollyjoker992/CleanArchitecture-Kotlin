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
    lateinit var expiredAt: String
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
            blockNumber: Long, blockOffset: Long, offset: Long, expiredAt: String, payId: String,
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
}