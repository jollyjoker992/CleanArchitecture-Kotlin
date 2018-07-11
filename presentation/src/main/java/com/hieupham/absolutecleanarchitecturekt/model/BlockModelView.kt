package com.hieupham.absolutecleanarchitecturekt.model

import android.os.Parcel
import android.os.Parcelable
import com.hieupham.domain.entity.Block

class BlockModelView() : Parcelable, Mapable<Block, BlockModelView> {

    var number: Long = 0
    lateinit var hash: String
    lateinit var createdAt: String

    constructor(parcel: Parcel) : this() {
        number = parcel.readLong()
        hash = parcel.readString()
        createdAt = parcel.readString()
    }

    constructor(number: Long, hash: String, createdAt: String) : this() {
        this.number = number
        this.hash = hash
        this.createdAt = createdAt
    }

    override fun map(data: Block): BlockModelView {
        return BlockModelView(data.number, data.hash, data.createdAt)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(number)
        parcel.writeString(hash)
        parcel.writeString(createdAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BlockModelView) return false

        if (number != other.number) return false
        if (hash != other.hash) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + hash.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<BlockModelView> {
        override fun createFromParcel(parcel: Parcel): BlockModelView {
            return BlockModelView(parcel)
        }

        override fun newArray(size: Int): Array<BlockModelView?> {
            return arrayOfNulls(size)
        }
    }


}