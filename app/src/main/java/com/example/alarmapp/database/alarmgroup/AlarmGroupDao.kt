package com.example.alarmapp.database.alarmgroup

import androidx.room.Dao
import androidx.room.Query
import com.example.alarmapp.database.alarm.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmGroupDao {
    @Query("select * from alarm_group")
    fun getAlarmGroups(): Flow<List<AlarmGroupEntity>>
}