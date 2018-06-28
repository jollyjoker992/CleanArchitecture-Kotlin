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

    companion object CREATOR : Parcelable.Creator<BlockModelView> {
        override fun createFromParcel(parcel: Parcel): BlockModelView {
            return BlockModelView(parcel)
        }

        override fun newArray(size: Int): Array<BlockModelView?> {
            return arrayOfNulls(size)
        }
    }
}