package com.example.alarmapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

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