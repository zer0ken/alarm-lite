package com.example.alarmapp.database.alarm

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.alarmapp.database.alarmgroup.AlarmGroupEntity

@Entity(
    tableName = "alarm", foreignKeys = [ForeignKey(
        entity = AlarmGroupEntity::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("groupName"),
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val hour: Int,
    val minute: Int,
    val repeatOnWeekdays: MutableList<Boolean>,
    val name: String,
    val groupName: String?,
    val isOn: Boolean,
    val isBookmarked: Boolean,
)
