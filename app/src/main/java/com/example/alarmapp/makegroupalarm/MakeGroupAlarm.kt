package com.example.alarmapp.makegroupalarm

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.addalarm.cancelsave.CancelSave
import com.example.alarmapp.makegroupalarm.groupalarmname.GroupAlarmName
import com.example.alarmapp.makegroupalarm.groupalarmrepeat.GroupAlarmRepeat
import com.example.alarmapp.makegroupalarm.groupalarmsound.GroupAlarmSound
import com.example.alarmapp.makegroupalarm.grouprepeatweek.GroupRepeatWeek
import com.example.alarmapp.makegroupalarm.groupvibrator.GroupVibrator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MakeGroupAlarm(navController: NavController) {
    val alarmName = remember { mutableStateOf("") }
    val repeatDays = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    val ringtoneIsOn = remember { mutableStateOf(true) }
    val vibrationIsOn = remember { mutableStateOf(true) }
    val repeatIsOn = remember { mutableStateOf(true) }
    val selectedSoundUri = remember{ mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val verticalSpace = 24.dp
    Scaffold(
        bottomBar = {CancelSave(navController)}
    ) {
        Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
        ) {
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(verticalSpace))
                GroupRepeatWeek(repeatDays = repeatDays)
                Spacer(modifier = Modifier.height(verticalSpace))
                GroupAlarmName(alarmName = alarmName)
                Spacer(modifier = Modifier.height(12.dp))
                GroupAlarmSound(ringtoneIsOn, context ,selectedSoundUri)
                Divider(modifier = Modifier.padding(vertical=12.dp) ,color = Color.LightGray)
                GroupVibrator(context, vibrationIsOn)
                Divider(modifier = Modifier.padding(vertical=12.dp) ,color = Color.LightGray)
                GroupAlarmRepeat(repeatIsOn, navController)
                Spacer(modifier = Modifier.height(verticalSpace))
            }
        }
    }
}