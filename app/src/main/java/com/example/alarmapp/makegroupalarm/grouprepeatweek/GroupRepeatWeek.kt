package com.example.alarmapp.makegroupalarm.grouprepeatweek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun GroupRepeatWeek(repeatDays: SnapshotStateList<Boolean>) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        GroupRepeatDay(whatDayIndex = 6, whatDayColor = Color.Red, repeatDays)
        GroupRepeatDay(whatDayIndex = 0, whatDayColor = Color.Black, repeatDays)
        GroupRepeatDay(whatDayIndex = 1, whatDayColor = Color.Black, repeatDays)
        GroupRepeatDay(whatDayIndex = 2, whatDayColor = Color.Black, repeatDays)
        GroupRepeatDay(whatDayIndex = 3, whatDayColor = Color.Black, repeatDays)
        GroupRepeatDay(whatDayIndex = 4, whatDayColor = Color.Black, repeatDays)
        GroupRepeatDay(whatDayIndex = 5, whatDayColor = Color.Blue, repeatDays)
    }
}