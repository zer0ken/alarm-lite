package com.example.alarmapp.makegroupalarm.grouprepeatweek

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GroupRepeatDay(
    whatDayIndex: Int,
    whatDayColor: Color,
    repeatDays: SnapshotStateList<Boolean>
) {
    var colorText = if (repeatDays[whatDayIndex]) Color(0xFF734D4D) else whatDayColor
    var colorBorder = if (repeatDays[whatDayIndex]) Color(0xFF734D4D) else Color.Transparent
    val day = when (whatDayIndex) {
        6 -> "일"
        0 -> "월"
        1 -> "화"
        2 -> "수"
        3 -> "목"
        4 -> "금"
        5 -> "토"
        else -> "요일 오류"
    }
    Text(
        text = day,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color= colorText,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .size(24.dp)
            .clickable { repeatDays[whatDayIndex] = !repeatDays[whatDayIndex] }
            .border(
                width = 1.dp,
                color = colorBorder,
                shape = CircleShape
            )
            .wrapContentSize(align = Alignment.Center)
    )
}