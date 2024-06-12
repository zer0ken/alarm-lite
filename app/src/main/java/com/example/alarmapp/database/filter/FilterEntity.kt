package com.example.alarmapp.database.filter

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarmapp.filter.GroupFilter
import com.example.alarmapp.filter.RepeatFilter

@Entity(tableName = "filter")
data class FilterEntity(
    @PrimaryKey
    val name: String,
    val repeatFilter: RepeatFilter?,
    val groupFilter: GroupFilter?
)