package com.example.alarmapp.database.alarm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("select * from alarm")
    fun getAll(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarmEntity: AlarmEntity)

    @Update
    fun update(alarmEntity: AlarmEntity)

    @Delete
    fun delete(alarmEntity: AlarmEntity)
}