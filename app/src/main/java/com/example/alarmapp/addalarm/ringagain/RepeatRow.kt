package com.example.alarmapp.addalarm.ringagain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun RepeatRow(
    colIndex : Int,
    number: Int,
    repeatCheckList : SnapshotStateList<Boolean>,
    alarmViewModel: AlarmViewModel
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column () {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = repeatCheckList[colIndex],
                    onCheckedChange = {
                        for ( i in 0 until repeatCheckList.size){
                            repeatCheckList[i] = (i == colIndex)
                        }
                        alarmViewModel.setRepeatNumber(number)
                    }
                )
                when(number){
                    3 -> Text(text = "3회")
                    5 -> Text(text = "5회")
                    100000 -> Text(text = "계속 반복")
                }
            }
            if(number !=100000)
                HorizontalDivider(modifier = Modifier.padding(start = 48.dp,end = 12.dp), color= Color.LightGray)
        }
    }
}