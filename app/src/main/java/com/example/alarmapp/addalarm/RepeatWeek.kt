package com.example.alarmapp.addalarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RepeatWeek(repeatDays: SnapshotStateList<Boolean>) {
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
}