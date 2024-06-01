package com.example.alarmapp.addalarm.alarmrepeat

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

@Composable
fun GapRow(
    colIndex : Int,
    time: Int,
    gapCheckList :SnapshotStateList<Boolean>
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
                    checked = gapCheckList[colIndex],
                    onCheckedChange = {
                        for ( i in 0 until gapCheckList.size){
                            gapCheckList[i] = (i == colIndex)
                        }
                    }
                )
                when(time){
                    5 -> Text(text = "5분")
                    10 -> Text(text = "10분")
                    15 -> Text(text = "15분")
                    30 -> Text(text = "30분")
                }
            }
            if(time !=30)
                HorizontalDivider(modifier = Modifier.padding(start = 48.dp,end = 12.dp), color= Color.LightGray)
        }
    }
}