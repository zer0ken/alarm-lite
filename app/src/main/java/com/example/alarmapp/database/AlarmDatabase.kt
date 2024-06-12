package com.example.alarmapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.alarmapp.database.alarm.AlarmDao
import com.example.alarmapp.database.alarm.AlarmEntity
import com.example.alarmapp.database.alarmgroup.AlarmGroupDao
import com.example.alarmapp.database.alarmgroup.AlarmGroupEntity
import com.example.alarmapp.database.filter.FilterDao
import com.example.alarmapp.database.filter.FilterEntity

@Database(
    entities = [AlarmEntity::class, AlarmGroupEntity::class, FilterEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    abstract fun alarmGroupDao(): AlarmGroupDao

    abstract fun filterDao(): FilterDao

    companion object {
        private var instance: AlarmDatabase? = null
        fun getInstance(context: Context): AlarmDatabase = instance
            ?: Room.databaseBuilder(
                context,
                AlarmDatabase::class.java,
                "alarm_database"
            ).build().also { instance = it }
    }
}