package com.example.alarmapp.addalarm.timepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun TimePicker(selectedHour: MutableState<Int> , selectedMinute: MutableState<Int> ) {
    val hourListState = rememberLazyListState()
    val minuteListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit){
        coroutineScope.launch {
            hourListState.scrollToItem(47) //초기 0시
            minuteListState.scrollToItem(119) // 초기 00분
        }
    }

    LaunchedEffect(hourListState){
        snapshotFlow { hourListState.firstVisibleItemIndex + (hourListState.firstVisibleItemScrollOffset)/80 +1} // 다시...
            .distinctUntilChanged()
            .filter { it in 0 until 24* 4 }
            .collect{index ->
                selectedHour.value = index % 24
            }
    }

    LaunchedEffect(minuteListState){
        snapshotFlow { minuteListState.firstVisibleItemIndex + (minuteListState.firstVisibleItemScrollOffset)/80 +1} // 다시...
            .distinctUntilChanged()
            .filter { it in 0 until 60* 4 }
            .collect{index ->
                selectedMinute.value = index % 60
            }
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(288.dp)
            .padding(24.dp)
    ) {
        LazyColumn(
            state = hourListState,
            modifier = Modifier
                .size(width = 60.dp, height = 240.dp)
        ) {
            items(24*4) { index ->
                val hour = index % 24
                val isCenter = hour == selectedHour.value
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            selectedHour.value = hour
                            coroutineScope.launch {
                                hourListState.animateScrollToItem(hour - 1)
                            }
                        }
                ){
                    Text(
                        text = hour.toString(),
                        fontSize = 40.sp,
                        color= if (isCenter) Color.Black else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(40.dp))
        Text(text = ":", fontSize = 40.sp)
        Spacer(modifier = Modifier.width(40.dp))
        LazyColumn(
            state = minuteListState,
            modifier = Modifier
                .size(width = 60.dp, height = 240.dp)
        ) {
            items(60*4) { index ->
                val minute = index % 60
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            selectedMinute.value = minute
                            coroutineScope.launch {
                                minuteListState.scrollToItem(minute - 1)
                            }
                        }
                ){
                    Text(
                        text = "%02d".format(minute),
                        fontSize = 40.sp,
                        color= if (selectedMinute.value == minute) Color.Black else Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}
