package com.example.alarmapp.database.alarm

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("select * from alarm")
    fun getAlarms(): Flow<List<AlarmEntity>>
}