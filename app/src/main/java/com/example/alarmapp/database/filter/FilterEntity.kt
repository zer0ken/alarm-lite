package com.example.alarmapp.database.filter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filter")
data class FilterEntity(
    @PrimaryKey val name: String,
    val repeatFilter: MutableList<Boolean>,
    val groupFilter: MutableList<String>?
)

//@Entity(tableName = "filter")
//data class FilterEntity(
//    @PrimaryKey
//    val name: String,
//    val repeatFilter: RepeatFilter?,
//    val groupFilter: GroupFilter?
//)