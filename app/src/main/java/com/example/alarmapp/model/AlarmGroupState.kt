package com.example.alarmapp.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class AlarmGroupState(
    groupName: String,
    isFolded: Boolean = true
) {
    var groupName by mutableStateOf(groupName)
    var isFolded by mutableStateOf(isFolded)
}

@Composable
fun rememberAlarmGroup(
    groupName: String,
    isFolded: Boolean = true
): AlarmGroupState {
    return remember {
        AlarmGroupState(
            groupName,
            isFolded
        )
    }
}