package com.example.alarmapp.model

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters

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
    isSelected: Boolean = false,
    isRingtoneOn: Boolean = false,
    selectedRingtoneUri: Uri? = null
) {
    var hour by mutableStateOf(hour)
    var minute by mutableStateOf(minute)
    val repeatOnWeekdays = mutableStateListOf<Boolean>().also { it.addAll(repeatOnWeekdays) }
    var name by mutableStateOf(name)
    var groupName by mutableStateOf(groupName)
    var isBookmarked by mutableStateOf(isBookmarked)
    var isOn by mutableStateOf(isOn)
    var isSelected by mutableStateOf(isSelected)
    var isRingtoneOn by mutableStateOf(isRingtoneOn)
    var selectedRingtoneUri by mutableStateOf(selectedRingtoneUri)

    fun getNextRingTime(): LocalDateTime {
        val now = LocalDateTime.now()

        var targetTime = LocalDateTime.now()
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        if (repeatOnWeekdays.any { it }) {
            var minDiff = Duration.ofDays(7)
            var next = targetTime.plusDays(7)

            repeatOnWeekdays.forEachIndexed { dayOfWeek, doRepeat ->
                if (doRepeat){
                    val _dayOfWeek = if (dayOfWeek == 0) DayOfWeek.SUNDAY else DayOfWeek.of(dayOfWeek)
                    var _next = targetTime.with(TemporalAdjusters.nextOrSame(_dayOfWeek))
                    if (_next.isBefore(now)) {
                        _next = _next.plusDays(7)
                    }
                    val _minDiff = Duration.between(now, _next)
                    if (_minDiff < minDiff) {
                        next = _next
                        minDiff = _minDiff
                    }
                }
            }
            targetTime = next
        } else if (targetTime.isBefore(now)) {
            targetTime = targetTime.plusDays(1)
        }

        return targetTime
    }
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
    isSelected: Boolean = false,
    isRingtoneOn: Boolean = false,
    selectedRingtoneUri: Uri? = null
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
            isSelected = isSelected,
            isRingtoneOn = isRingtoneOn,
            selectedRingtoneUri = selectedRingtoneUri
        )
    }
}