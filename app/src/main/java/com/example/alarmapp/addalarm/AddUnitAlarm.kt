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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.R
import com.example.alarmapp.addalarm.alarmgroupselect.AlarmGroupSelect
import com.example.alarmapp.addalarm.alarmname.AlarmName
import com.example.alarmapp.addalarm.alarmrepeat.AlarmRepeat
import com.example.alarmapp.addalarm.bookmark.Bookmark
import com.example.alarmapp.addalarm.cancelsave.CancelSave
import com.example.alarmapp.addalarm.repeatweek.RepeatWeek
import com.example.alarmapp.addalarm.timepicker.TimePicker
import com.example.alarmapp.alarmdata.AlarmManager
import com.example.alarmapp.model.AlarmViewModel

@Composable
fun AddUnitAlarm(navController: NavController, alarmManager: AlarmManager, alarmViewModel: AlarmViewModel) {
    var alarmName = remember { mutableStateOf("") }
    val selectedHour = remember { mutableStateOf(0) }
    val selectedMinute = remember{ mutableStateOf(0)}
    var repeatDays = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    var bookmark = remember { mutableStateOf(false)}
    val isOn = remember { mutableStateOf(true)}

    val verticalSpace = 16.dp

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
    ) {
        TimePicker(selectedHour = selectedHour, selectedMinute = selectedMinute)
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
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(text =  stringResource(id = R.string.alarm_sound))
                Switch(
                    checked = isOn.value,
                    onCheckedChange = {isOn.value = !isOn.value},
                    modifier = Modifier
                        .scale(0.6f)
                )
            }

            //진동
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text =  stringResource(id = R.string.vibration))
                Switch(
                    checked = isOn.value,
                    onCheckedChange = {isOn.value = !isOn.value},
                    modifier = Modifier
                        .scale(0.6f)
                )
            }

            AlarmRepeat(isOn, navController)
            Spacer(modifier = Modifier.height(verticalSpace))
        }
        Spacer(modifier = Modifier.weight(1f))
        CancelSave(navController)
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