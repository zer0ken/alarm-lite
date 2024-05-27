package com.example.alarmapp.alarmdata

data class Alarm (
    var content: String,
    var hour: Int,          // 00 ~ 23
    var minute: Int,        // 00 ~ 59
    var isOn: Boolean
)