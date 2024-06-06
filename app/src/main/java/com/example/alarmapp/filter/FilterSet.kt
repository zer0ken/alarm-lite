package com.example.alarmapp.filter

import java.time.DayOfWeek
import java.time.MonthDay

data class FilterSet(
    val title: String,
    val repeatFilter: List<RepeatFilter>? = null,
    val groupFilter: List<GroupFilter>? = null
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

// 임의로 만든 알람 그룹 data class
data class AlarmGroup(
    val name: String
)