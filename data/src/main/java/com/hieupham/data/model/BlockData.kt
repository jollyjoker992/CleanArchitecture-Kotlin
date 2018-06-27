package com.hieupham.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hieupham.domain.entity.Block

/**
 * Created by hieupham on 6/26/18.
 */
@Entity(tableName = "Block")
data class BlockData(
        @Expose @PrimaryKey
        val number: Long,

        @Expose
        val hash: String,

        @Expose
        @ColumnInfo(name = "created_at")
        @SerializedName("created_at")
        val createdAt: String) : Mapable<Block> {

    override fun map(): Block {
        return Block(number = number, hash = hash, createdAt = createdAt)
    }
}