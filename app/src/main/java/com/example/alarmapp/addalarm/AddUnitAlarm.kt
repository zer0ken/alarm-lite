package com.example.alarmapp.addalarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.addalarm.alarmgroupselect.AlarmGroupSelect
import com.example.alarmapp.addalarm.alarmname.AlarmName
import com.example.alarmapp.addalarm.alarmsound.AlarmSound
import com.example.alarmapp.addalarm.bookmark.Bookmark
import com.example.alarmapp.addalarm.cancelsave.CancelSave
import com.example.alarmapp.addalarm.repeatweek.RepeatWeek
import com.example.alarmapp.addalarm.ringagain.RingAgain
import com.example.alarmapp.addalarm.timepicker.TimePicker
import com.example.alarmapp.addalarm.vibrator.Vibrator
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun AddUnitAlarm(navController: NavController, alarmViewModel: AlarmViewModel) {
    val context = LocalContext.current
    val verticalSpace = 24.dp
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
    ) {
        TimePicker(alarmViewModel)
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(verticalSpace))
            RepeatWeek(alarmViewModel)
            Spacer(modifier = Modifier.height(verticalSpace))
            AlarmName(alarmViewModel)
            Spacer(modifier = Modifier.height(12.dp))
            AlarmGroupSelect(alarmViewModel)
            Divider(modifier = Modifier.padding(vertical=12.dp) ,color = Color.LightGray)
            Bookmark(alarmViewModel)
            Divider(modifier = Modifier.padding(vertical=12.dp) ,color = Color.LightGray)
            AlarmSound(context, alarmViewModel)
            Divider(modifier = Modifier.padding(vertical=12.dp) ,color = Color.LightGray)
            Vibrator(context, alarmViewModel)
            Divider(modifier = Modifier.padding(vertical=12.dp) ,color = Color.LightGray)
            RingAgain(navController, alarmViewModel)
            Spacer(modifier = Modifier.height(verticalSpace))
        }
        Spacer(modifier = Modifier.weight(1f))
        CancelSave(context,navController,alarmViewModel)
    }
}

@Preview
@Composable
fun PrevAddUnitAlarm() {
    val navController = rememberNavController()
    val alarmViewModel = AlarmViewModel()
    AddUnitAlarm(navController, alarmViewModel)
}