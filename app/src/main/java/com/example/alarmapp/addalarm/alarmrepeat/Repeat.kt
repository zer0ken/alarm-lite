package com.example.alarmapp.addalarm.alarmrepeat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.alarmapp.R
import com.example.alarmapp.alarmdata.AlarmManager

@Composable
fun Repeat(repeatCheckList : SnapshotStateList<Boolean>, alarmManager: AlarmManager) {
    val isOn = remember { mutableStateOf(true) } // 확인용이고 뷰모델 합쳐지면 상단의 사용 중 버튼 과 연결
    val alpha = if (isOn.value) 1f else 0.5f
    Text(
        text = stringResource(id = R.string.repeat),
        modifier = Modifier
            .padding(start =12.dp,top = 12.dp, bottom = 4.dp)
    )
    Column (
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { isOn.value = !isOn.value } // 추후 변경
            .alpha(alpha)
    ) {
        Column () {
            RepeatRow(colIndex = 0, time = 3, repeatCheckList = repeatCheckList)
            RepeatRow(colIndex = 1, time = 5, repeatCheckList = repeatCheckList)
            RepeatRow(colIndex = 2, time = 100000, repeatCheckList = repeatCheckList)
        }
    }
}