package com.example.alarmapp.filter

import com.example.alarmapp.alarmdata.AlarmGroup
import java.time.DayOfWeek
import java.time.MonthDay

data class FilterSet(
    val title: String,
    val repeatFilter: RepeatFilter? = null,
    val groupFilter: GroupFilter? = null
)

data class RepeatFilter(
    val week: List<DayOfWeek>? = null,
    val month: List<Int>? = null,
    val year: List<MonthDay>? = null
)

data class GroupFilter(
    val none: Boolean = false,
    val group: List<AlarmGroup>? = null
)