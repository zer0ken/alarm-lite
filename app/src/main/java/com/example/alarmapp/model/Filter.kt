package com.example.alarmapp.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.MonthDay

class Filter(
    name: String = "",
    repeatFilter: MutableList<Boolean> = mutableListOf(
        false,  // 일
        false,  // 월
        false,  // 화
        false,  // 수
        false,  // 목
        false,  // 금
        false   // 토
    ),
    groupFilter: MutableList<String> = mutableListOf()
) {
    var name by mutableStateOf(name)
    val repeatFilter = mutableStateListOf<Boolean>().also { it.addAll(repeatFilter) }
    val groupFilter = mutableStateListOf<String>().apply { addAll(groupFilter) }
}

@Composable
fun rememberFilter(
    name: String = "",
    repeatFilter: MutableList<Boolean> = mutableListOf(
        false,  // 일
        false,  // 월
        false,  // 화
        false,  // 수
        false,  // 목
        false,  // 금
        false   // 토
    ),
    groupFilter: MutableList<String> = mutableListOf()
): Filter {
    return remember {
        Filter(
            name = name,
            repeatFilter = repeatFilter,
            groupFilter = groupFilter
        )
    }
}

//data class Filter(
//    val name: String,
//    val repeatFilter: RepeatFilter? = null,
//    val groupFilter: GroupFilter? = null
//)
//
//
//data class RepeatFilter(
//    val week: List<DayOfWeek>? = null
//)
//
//
//data class GroupFilter(
//    val none: Boolean = false,
//    val group: List<AlarmGroupState>? = null
//)