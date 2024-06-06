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
    fun getAll(): Flow<List<AlarmGroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarmGroupEntity: AlarmGroupEntity)

    @Update
    fun update(alarmGroupEntity: AlarmGroupEntity)

    @Delete
    fun delete(alarmGroupEntity: AlarmGroupEntity)
}