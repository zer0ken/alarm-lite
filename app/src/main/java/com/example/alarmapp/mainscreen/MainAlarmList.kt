package com.example.alarmapp.mainscreen


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alarmapp.alarmdata.AlarmViewModel
import com.example.alarmapp.view.alarm.AlarmItemView


@Composable
fun MainAlarmList(alarmViewModel: AlarmViewModel) {
    val alarmList = alarmViewModel.getAlarmList()
    for (i in alarmList.indices){
        AlarmItemView(alarm = alarmList[i], alarmViewModel = alarmViewModel, modifier = Modifier)
    }


//    alarmManager.addAlarm(Alarm("Morning Alarm", 7, 30, remember { mutableStateListOf(false, false, false, false, false, false, false) }, updatedTime = System.currentTimeMillis()))
//    alarmManager.addAlarm(Alarm("Lunch Break Alarm", 12, 0, remember { mutableStateListOf(false, false, false, false, false, false, false) }, updatedTime = System.currentTimeMillis()))
//    alarmManager.addAlarm(Alarm("Evening Alarm", 18, 0,remember { mutableStateListOf(false, false, false, false, false, false, false) }, updatedTime = System.currentTimeMillis()))
//    AlarmItemView(alarm = alarmManager.alarmList[0], alarmViewModel = alarmViewModel, modifier = Modifier)
//    AlarmItemView(alarm = alarmManager.alarmList[1], alarmViewModel = alarmViewModel, modifier = Modifier)
//    AlarmItemView(alarm = alarmManager.alarmList[2], alarmViewModel = alarmViewModel, modifier = Modifier)


    //    alarmManager.addGroup(AlarmGroup("abc"))
//    alarmManager.addGroup(AlarmGroup("def"))
//    alarmManager.addGroup(AlarmGroup("ghj"))
}

