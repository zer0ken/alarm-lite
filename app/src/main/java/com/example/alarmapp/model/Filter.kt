package com.example.alarmapp.model

import java.time.DayOfWeek
import java.time.MonthDay

data class Filter(
    val title: String,
    val repeatFilter: RepeatFilter? = null,
    val groupFilter: GroupFilter? = null
)

data class RepeatFilter(
    val week: List<DayOfWeek>? = null
)

data class GroupFilter(
    val none: Boolean = false,
    val group: List<AlarmGroupState>? = null
)