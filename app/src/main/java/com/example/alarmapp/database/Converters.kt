package com.example.alarmapp.database

import android.net.Uri
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromMutableList(value: MutableList<Boolean>): String = Json.encodeToString(value)

    @TypeConverter
    fun toMutableList(value: String): MutableList<Boolean> = Json.decodeFromString(value)


    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

//    @TypeConverter
//    fun fromRepeatFilter(value: RepeatFilter): String = Json.encodeToString(value)
//
//    @TypeConverter
//    fun toRepeatFilter(value: String): RepeatFilter = Json.decodeFromString(value)
//
//    @TypeConverter
//    fun fromGroupFilter(value: GroupFilter): String = Json.encodeToString(value)
//
//    @TypeConverter
//    fun toGroupFilter(value: String): GroupFilter = Json.decodeFromString(value)

    @TypeConverter
    fun toGroupFilter(value: String): GroupFilter = Json.decodeFromString(value)

    @TypeConverter
    fun fromUri(value: Uri?) = value?.toString()

    @TypeConverter
    fun toUri(value: String?): Uri? = value?.let { Uri.parse(it) }
}