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
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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
    isRingtoneOn: Boolean = true,
    selectedRingtoneUri: Uri? = null,
    startDate: Long? = null,
    expireDate: Long? = null
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
    var startDate: Long? by mutableStateOf(startDate)
    var expireDate: Long? by mutableStateOf(expireDate)

    fun getNextRingTime(): LocalDateTime {
        var targetTime = if (startDate == null) {
            LocalDateTime.now()
        } else {
            LocalDateTime.ofEpochSecond(startDate!! / 1000, 0, ZoneOffset.UTC)
        }
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)
        while (targetTime.isBefore(LocalDateTime.now())) {
            targetTime = targetTime.plusDays(1)
        }

        if (repeatOnWeekdays.any { it }) {
            var minDiff = Duration.ofDays(7)
            var next = targetTime.plusDays(7)

            repeatOnWeekdays.forEachIndexed { dayOfWeek, doRepeat ->
                if (doRepeat) {
                    val _dayOfWeek =
                        if (dayOfWeek == 0) DayOfWeek.SUNDAY else DayOfWeek.of(dayOfWeek)
                    val _next = targetTime.with(TemporalAdjusters.nextOrSame(_dayOfWeek))
                    val _minDiff = Duration.between(targetTime, _next)
                    if (_minDiff < minDiff) {
                        next = _next
                        minDiff = _minDiff
                    }
                }
            }
            targetTime = next
        }

        return targetTime
    }

    fun getSpecifiedDateRange(): String? {
        val start = startDate?.let {
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(
                LocalDateTime.ofEpochSecond(
                    startDate!! / 1000, 0,
                    ZoneOffset.UTC
                )
            )
        }

        val end = expireDate?.let {
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(
                LocalDateTime.ofEpochSecond(
                    expireDate!! / 1000, 0,
                    ZoneOffset.UTC
                )
            )
        }

        return if (end != null) {
            "기간: $start ~ $end"
        } else if (start != null) {
            "시작: $start"
        } else {
            null
        }
    }
}

@Composable
fun rememberAlarmState(
): AlarmState {
    return remember {
        AlarmState()
    }
}