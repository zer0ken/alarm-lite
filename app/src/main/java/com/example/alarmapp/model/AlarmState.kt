package com.example.alarmapp.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Stable
class AlarmState(
    val id: Int = 0,
    hour: Int = LocalDateTime.now().hour,
    minute: Int = LocalDateTime.now().minute,
    repeatOnWeekdays: MutableList<Boolean> = mutableListOf(
        false,  // 일
        false,  // 월
        false,  // 화
        false,  // 수
        false,  // 목
        false,  // 금
        false   // 토
    ),
    name: String = "",
    groupName: String = "",
    isBookmarked: Boolean = false,
    isOn: Boolean = true,
    isSelected: Boolean = false
) {
    var hour by mutableStateOf(hour)
    var minute by mutableStateOf(minute)
    val repeatOnWeekdays = mutableStateListOf<Boolean>().also { it.addAll(repeatOnWeekdays) }
    var name by mutableStateOf(name)
    var groupName by mutableStateOf(groupName)
    var isBookmarked by mutableStateOf(isBookmarked)
    var isOn by mutableStateOf(isOn)
    var isSelected by mutableStateOf(isSelected)
}

@Composable
fun rememberAlarmState(
    id: Int = 0,
    hour: Int = LocalDateTime.now().hour,
    minute: Int = LocalDateTime.now().minute,
    repeatOnWeekdays: MutableList<Boolean> = mutableListOf(
        false,  // 일
        false,  // 월
        false,  // 화
        false,  // 수
        false,  // 목
        false,  // 금
        false   // 토
    ),
    name: String = "",
    groupName: String = "",
    isBookmarked: Boolean = false,
    isOn: Boolean = true,
    isSelected: Boolean = false
): AlarmState {
    return remember {
        AlarmState(
            id = id,
            hour = hour,
            minute = minute,
            repeatOnWeekdays = repeatOnWeekdays,
            name = name,
            groupName = groupName,
            isBookmarked = isBookmarked,
            isOn = isOn,
            isSelected = isSelected
        )
    }
}