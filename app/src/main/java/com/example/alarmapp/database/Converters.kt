package com.example.alarmapp.database

import androidx.room.TypeConverter
import com.example.alarmapp.filter.GroupFilter
import com.example.alarmapp.filter.RepeatFilter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromRepeatFilter(value: RepeatFilter): String = Json.encodeToString(value);

    @TypeConverter
    fun toRepeatFilter(value: String): RepeatFilter = Json.decodeFromString(value);

    @TypeConverter
    fun fromGroupFilter(value: GroupFilter): String = Json.encodeToString(value);

    @TypeConverter
    fun toGroupFilter(value: String): GroupFilter = Json.decodeFromString(value);
}