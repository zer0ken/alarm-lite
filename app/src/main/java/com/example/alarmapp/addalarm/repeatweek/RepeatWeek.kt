package com.example.alarmapp.addalarm.repeatweek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun RepeatWeek(alarmViewModel: AlarmViewModel) {
    val repeatDays = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        RepeatDay(whatDayIndex = 6, whatDayColor = Color.Red, repeatDays)
        RepeatDay(whatDayIndex = 0, whatDayColor = Color.Black, repeatDays)
        RepeatDay(whatDayIndex = 1, whatDayColor = Color.Black, repeatDays)
        RepeatDay(whatDayIndex = 2, whatDayColor = Color.Black, repeatDays)
        RepeatDay(whatDayIndex = 3, whatDayColor = Color.Black, repeatDays)
        RepeatDay(whatDayIndex = 4, whatDayColor = Color.Black, repeatDays)
        RepeatDay(whatDayIndex = 5, whatDayColor = Color.Blue, repeatDays)
    }
    alarmViewModel.setRepeatDays(repeatDays)
}