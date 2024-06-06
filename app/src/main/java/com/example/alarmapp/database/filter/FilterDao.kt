package com.example.alarmapp.database.filter

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alarmapp.database.alarmgroup.AlarmGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDao {
    @Query("select * from filter")
    fun getAll(): Flow<List<FilterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(filterEntity: FilterEntity)

    @Update
    fun update(filterEntity: FilterEntity)

    @Delete
    fun delete(filterEntity: FilterEntity)
}