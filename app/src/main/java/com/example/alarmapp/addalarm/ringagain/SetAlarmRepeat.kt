package com.example.alarmapp.addalarm.ringagain


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun SetAlarmRepeat(navController: NavController, alarmViewModel: AlarmViewModel) {
    val gapCheckList = remember { mutableStateListOf(true,false,false,false)}
    val repeatCheckList = remember { mutableStateListOf(true,false,false)}
    val isOn = remember { mutableStateOf(alarmViewModel.getRingAgain())}

    val gapList = listOf(5,10,15,30)
    val repeatList = listOf(3,5,100000)

    for (i in 0 until gapCheckList.size){
        if (gapCheckList[i])
            alarmViewModel.setRepeatGap(gapList[i])
    }

    for (i in 0 until repeatCheckList.size){
        if (repeatCheckList[i])
            alarmViewModel.setRepeatNumber(repeatList[i])
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
    ) {
        BackwardAndTitle(navController)
        UsingOrNot(alarmViewModel, isOn)
        Gap(gapCheckList, alarmViewModel, isOn)
        Repeat(repeatCheckList, alarmViewModel, isOn)
    }
}
