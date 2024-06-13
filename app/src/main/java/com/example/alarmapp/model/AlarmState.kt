package com.example.alarmapp.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDateTime

@Stable
class AlarmState(
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
    bookmarked: Boolean = false,
    isOn: Boolean = true,
    isSelected: Boolean = false
) {
    val id: Int = 0
    var hour by mutableStateOf(hour)
    var minute by mutableStateOf(minute)
    val repeatOnWeekdays = mutableStateListOf<Boolean>().also { it.addAll(repeatOnWeekdays) }
    var name by mutableStateOf(name)
    var groupName by mutableStateOf(groupName)
    var bookmarked by mutableStateOf(bookmarked)
    var isOn by mutableStateOf(isOn)
    var isSelected by mutableStateOf(isSelected)
}

@Composable
fun rememberAlarmState(
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
    bookmarked: Boolean = false,
    isOn: Boolean = true,
    isSelected: Boolean = false
): AlarmState {
    return remember {
        AlarmState(
            hour,
            minute,
            repeatOnWeekdays,
            name,
            groupName,
            bookmarked,
            isOn,
            isSelected
        )
    }
}