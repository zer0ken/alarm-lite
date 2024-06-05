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
    @PrimaryKey val id: Int,
    val groupName: String?, // 그룹 이름
    val content: String,    // 알람 설명
    val hour: Int,          // 울리는 시간 0..23
    val minute: Int,        // 울리는 분 0..59
    val weekTerm: Int,      // 반복 주기 1..
    val ringCount: Int,     // 울린 횟수 0..
    val ringLimit: Int?,    // 울림 횟수 제한 0(제한 없음)..
    val leftMute: Int,     // 남은 뮤트 횟수 0..
    val lastModified: Long, // 마지막으로 수정된 시간
    val isOn: Boolean,      // 켜져있는지 여부
    val isPinned: Boolean,   // 상단에 고정된 알람인지 여부
    // 특정 요일에 반복 여부
    val ringOnMon: Boolean,
    val ringOnTue: Boolean,
    val ringOnWed: Boolean,
    val ringOnThu: Boolean,
    val ringOnFri: Boolean,
    val ringOnSat: Boolean,
    val ringOnSun: Boolean
)
