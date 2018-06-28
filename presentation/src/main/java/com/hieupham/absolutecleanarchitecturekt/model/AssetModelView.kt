package com.hieupham.absolutecleanarchitecturekt.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.internal.LinkedTreeMap
import com.hieupham.domain.entity.Asset

class AssetModelView() : Parcelable, Mapable<Asset, AssetModelView> {

    lateinit var id: String
    lateinit var name: String
    lateinit var fingerPrint: String
    var metadata: LinkedTreeMap<String, String>? = null
    lateinit var registrant: String
    lateinit var status: String
    var blockNumber: Long = 0
    var blockOffset: Long = 0
    lateinit var expiresAt: String
    var offset: Long = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        fingerPrint = parcel.readString()
        registrant = parcel.readString()
        status = parcel.readString()
        blockNumber = parcel.readLong()
        blockOffset = parcel.readLong()
        expiresAt = parcel.readString()
        offset = parcel.readLong()
    }

    constructor(id: String, name: String, fingerPrint: String,
            metadata: LinkedTreeMap<String, String>?, registrant: String, status: String,
            blockNumber: Long, blockOffset: Long, expiresAt: String, offset: Long) : this() {
        this.id = id
        this.name = name
        this.fingerPrint = fingerPrint
        this.metadata = metadata
        this.registrant = registrant
        this.status = status
        this.blockNumber = blockNumber
        this.blockOffset = blockOffset
        this.expiresAt = expiresAt
        this.offset = offset
    }

    override fun map(data: Asset): AssetModelView {
        return AssetModelView(id = data.id, name = data.name, fingerPrint = data.fingerPrint,
                metadata = data.metadata, registrant = data.registrant, status = data.status,
                blockNumber = data.blockNumber, blockOffset = data.blockOffset,
                expiresAt = data.expiresAt, offset = data.offset)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(fingerPrint)
        parcel.writeString(registrant)
        parcel.writeString(status)
        parcel.writeLong(blockNumber)
        parcel.writeLong(blockOffset)
        parcel.writeString(expiresAt)
        parcel.writeLong(offset)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AssetModelView> {
        override fun createFromParcel(parcel: Parcel): AssetModelView {
            return AssetModelView(parcel)
        }

        override fun newArray(size: Int): Array<AssetModelView?> {
            return arrayOfNulls(size)
        }
    }

}