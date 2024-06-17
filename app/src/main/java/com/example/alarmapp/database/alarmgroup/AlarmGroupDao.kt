package com.example.alarmapp.database.alarmgroup

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmGroupDao {
    @Query("select * from alarm_group")
    suspend fun getAll(): List<AlarmGroupEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmGroupEntity: AlarmGroupEntity)

    @Update
    suspend fun update(alarmGroupEntity: AlarmGroupEntity)

    @Delete
    suspend fun delete(alarmGroupEntity: AlarmGroupEntity)
}