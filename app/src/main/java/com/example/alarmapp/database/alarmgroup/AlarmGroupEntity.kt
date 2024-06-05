package com.example.alarmapp.database.alarmgroup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_group")
data class AlarmGroupEntity(
    @PrimaryKey val name: String
)