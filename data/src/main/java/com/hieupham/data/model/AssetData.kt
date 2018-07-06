package com.hieupham.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import com.hieupham.domain.entity.Asset

/**
 * Created by hieupham on 6/26/18.
 */

@Entity(tableName = "Asset")
data class AssetData(
        @Expose
        @PrimaryKey
        val id: String,

        @Expose
        val name: String,

        @Expose
        @SerializedName("fingerprint")
        @ColumnInfo(name = "fingerprint")
        val fingerPrint: String?,

        @Expose
        val metadata: LinkedTreeMap<String, String>?,

        @Expose
        val registrant: String,

        @Expose
        val status: String,

        @Expose
        @SerializedName("block_number")
        @ColumnInfo(name = "block_number")
        val blockNumber: Long,

        @Expose
        @SerializedName("block_offset")
        @ColumnInfo(name = "block_offset")
        val blockOffset: Long,

        @Expose
        @SerializedName("expires_at")
        @ColumnInfo(name = "expires_at")
        val expiresAt: String?,

        @Expose
        val offset: Long) : Mapable<Asset> {

    override fun map(): Asset {
        return Asset(id = id, name = name, fingerPrint = fingerPrint, metadata = metadata,
                registrant = registrant, status = status, blockNumber = blockNumber,
                blockOffset = blockOffset, expiresAt = expiresAt, offset = offset)
    }


}