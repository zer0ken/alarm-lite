package com.example.alarmapp.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.R
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.alarmdata.AlarmManager
import com.example.alarmapp.model.AlarmViewModel
import com.example.alarmapp.view.IconToggleButton_

@Composable
fun AddUnitAlarm(navController: NavController, alarmManager: AlarmManager, alarmViewModel: AlarmViewModel) {
    var alarmName = remember { mutableStateOf("") }
    var repeatDays = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    var bookmark = remember { mutableStateOf(false)}

    var selectedHour = remember {mutableStateOf(0)}
    var selectedMinute = remember{ mutableStateOf(0)}

    alarmManager.addGroup(AlarmGroup("abc"))
    alarmManager.addGroup(AlarmGroup("def"))
    alarmManager.addGroup(AlarmGroup("ghj"))
    val verticalSpace = 16.dp
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(228.dp)
                .padding(24.dp)
        ) {


            LazyColumn(
                modifier = Modifier
                    .size(width = 60.dp, height = 180.dp)
            ) {
                items(24) { hour ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable { selectedHour.value = hour }
                    ){
                        Text(
                            text = hour.toString(),
                            fontSize = 40.sp,
                            color= if (selectedHour.value == hour) Color.Black else Color.LightGray,
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
                modifier = Modifier
                    .size(width = 60.dp, height = 180.dp)
            ) {
                items(60) { minute ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable { selectedMinute.value = minute }
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


        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(verticalSpace))
            RepeatWeek(repeatDays = repeatDays)
            Spacer(modifier = Modifier.height(verticalSpace))
            AlarmName(alarmName = alarmName)
            Spacer(modifier = Modifier.height(verticalSpace))
            AlarmGroupSelect(alarmManager = AlarmManager)
            Spacer(modifier = Modifier.height(verticalSpace))
            Bookmark(bookmark)
            Spacer(modifier = Modifier.height(verticalSpace))


            //알림음
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text =  stringResource(id = R.string.alarm_sound))
            }

            //진동
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text =  stringResource(id = R.string.vibration))
                IconToggleButton_(
                    painter = painterResource(id = R.drawable.baseline_unfold_less_24),
                    checked = true,
                    onCheckedChange = { },
                    contentDescription = "알람 추가 진동 on/off"
                )
            }

            //다시 울림
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text =  stringResource(id = R.string.ring_again))
                //몇회 다시 울릴 건지
            }
        }


        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = Color.Black
                )
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.save),
                    color = Color.Black

                )
            }
        }
    }
}

@Preview
@Composable
fun PrevAddUnitAlarm() {
    val navController = rememberNavController()
    val alarmManager = AlarmManager
    val alarmViewModel = AlarmViewModel()
    AddUnitAlarm(navController, alarmManager, alarmViewModel)
}