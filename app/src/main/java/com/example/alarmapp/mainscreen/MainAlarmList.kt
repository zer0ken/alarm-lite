package com.example.alarmapp.mainscreen


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.alarmdata.AlarmManager
import com.example.alarmapp.model.AlarmViewModel
import com.example.alarmapp.view.alarm.AlarmItemView

@Composable
fun MainAlarmList(alarmManager: AlarmManager, alarmViewModel: AlarmViewModel) {

    alarmManager.addAlarm(Alarm("Morning Alarm", 7, 30, updatedTime = System.currentTimeMillis()))
    alarmManager.addAlarm(Alarm("Lunch Break Alarm", 12, 0, updatedTime = System.currentTimeMillis()))
    alarmManager.addAlarm(Alarm("Evening Alarm", 18, 0, updatedTime = System.currentTimeMillis()))
    AlarmItemView(alarm = alarmManager.getAlarm(0), alarmViewModel = alarmViewModel, modifier = Modifier)
    AlarmItemView(alarm = alarmManager.getAlarm(1), alarmViewModel = alarmViewModel, modifier = Modifier)
    AlarmItemView(alarm = alarmManager.getAlarm(2), alarmViewModel = alarmViewModel, modifier = Modifier)
}

