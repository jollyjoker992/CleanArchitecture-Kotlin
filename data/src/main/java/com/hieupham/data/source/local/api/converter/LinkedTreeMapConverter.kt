package com.hieupham.data.source.local.api.converter

import android.arch.persistence.room.TypeConverter
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken

/**
 * Created by hieupham on 6/26/18.
 */
class LinkedTreeMapConverter {

    @TypeConverter
    fun toString(map: LinkedTreeMap<String, String>?): String? {
        return if (map == null || map.isEmpty()) null else Gson().toJsonTree(
                map).asJsonObject.toString()
    }

    @TypeConverter
    fun fromString(str: String): LinkedTreeMap<String, String>? {
        return if (TextUtils.isEmpty(str)) null else Gson().fromJson<LinkedTreeMap<String, String>>(
                str, object : TypeToken<LinkedTreeMap<String, String>>() {}.type)
    }
}