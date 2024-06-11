package com.example.alarmapp.addalarm.ringagain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.alarmapp.R
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun Gap(gapCheckList : SnapshotStateList<Boolean>, alarmViewModel: AlarmViewModel, isOn: MutableState<Boolean>) {
    val alpha = if (isOn.value) 1f else 0.5f
    Text(
        text = stringResource(id = R.string.gap),
        modifier = Modifier
            .padding(start =12.dp,top = 12.dp, bottom = 4.dp)
    )
    Column (
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Color.White)
            .alpha(alpha)
    ) {
        GapRow(colIndex = 0, time = 5, gapCheckList = gapCheckList,alarmViewModel, isOn)
        GapRow(colIndex = 1, time = 10, gapCheckList = gapCheckList,alarmViewModel,isOn)
        GapRow(colIndex = 2, time = 15, gapCheckList = gapCheckList,alarmViewModel, isOn)
        GapRow(colIndex = 3, time = 30, gapCheckList = gapCheckList, alarmViewModel, isOn)
    }
}