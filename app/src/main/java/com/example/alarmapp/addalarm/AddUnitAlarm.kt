package com.example.alarmapp.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    alarmManager.addGroup(AlarmGroup("abc"))
    alarmManager.addGroup(AlarmGroup("def"))
    alarmManager.addGroup(AlarmGroup("ghj"))
    val verticalSpace = 16.dp
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
    ) {
        Text(text = "알람 시간 스크롤 바 만들 예정")

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


            //알림음
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text =  stringResource(id = R.string.alarm_sound))
                //드롭 다운 구성 아이콘 하나??
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
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.cancel))
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@Preview
@Composable
fun Prev() {
    val navController = rememberNavController()
    val alarmManager = AlarmManager
    val alarmViewModel = AlarmViewModel()
    AddUnitAlarm(navController, alarmManager, alarmViewModel)
}