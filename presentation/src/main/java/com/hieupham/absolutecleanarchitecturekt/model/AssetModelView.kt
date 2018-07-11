package com.hieupham.absolutecleanarchitecturekt.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.internal.LinkedTreeMap
import com.hieupham.domain.entity.Asset

class AssetModelView() : Parcelable, Mapable<Asset, AssetModelView> {

    lateinit var id: String
    lateinit var name: String
    var fingerPrint: String? = null
    var metadata: LinkedTreeMap<String, String>? = null
    lateinit var registrant: String
    lateinit var status: String
    var blockNumber: Long = 0
    var blockOffset: Long = 0
    var expiresAt: String? = null
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

    constructor(id: String, name: String, fingerPrint: String?,
            metadata: LinkedTreeMap<String, String>?, registrant: String, status: String,
            blockNumber: Long, blockOffset: Long, expiresAt: String?, offset: Long) : this() {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AssetModelView) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (fingerPrint != other.fingerPrint) return false
        if (metadata != other.metadata) return false
        if (registrant != other.registrant) return false
        if (status != other.status) return false
        if (blockNumber != other.blockNumber) return false
        if (blockOffset != other.blockOffset) return false
        if (expiresAt != other.expiresAt) return false
        if (offset != other.offset) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (fingerPrint?.hashCode() ?: 0)
        result = 31 * result + (metadata?.hashCode() ?: 0)
        result = 31 * result + registrant.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + blockNumber.hashCode()
        result = 31 * result + blockOffset.hashCode()
        result = 31 * result + (expiresAt?.hashCode() ?: 0)
        result = 31 * result + offset.hashCode()
        return result
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