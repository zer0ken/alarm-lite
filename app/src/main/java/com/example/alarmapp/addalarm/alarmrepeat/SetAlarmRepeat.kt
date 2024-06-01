package com.example.alarmapp.addalarm.alarmrepeat


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alarmapp.alarmdata.AlarmManager

@Composable
fun SetAlarmRepeat(navController: NavController, alarmManager: AlarmManager) {
    val gapCheckList = remember { mutableStateListOf(true,false,false,false)}
    val repeatCheckList = remember { mutableStateListOf(true,false,false)}

    val gapList = listOf(5,10,15,30)
    val repeatList = listOf(3,5,100000)

    for (i in 0 until gapCheckList.size){ // 이 부분은 뷰모델이 하나로 합쳐지면 뷰모델에 박으면 됨
        if (gapCheckList[i])
            alarmManager.repeatGap = gapList[i]
    }

    for (i in 0 until repeatCheckList.size){ // 이 부분은 뷰모델이 하나로 합쳐지면 뷰모델에 박으면 됨
        if (repeatCheckList[i])
            alarmManager.repeatGap = repeatList[i]
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9E9E9))
    ) {
        BackwardAndTitle(navController)
        UsingOrNot(alarmManager)
        Gap(gapCheckList, alarmManager)
        Repeat(repeatCheckList, alarmManager)
    }
}

@Preview(showBackground = true)
@Composable
fun PrevAlarmRepeat() {
    val navController = rememberNavController()
    val alarmManager = AlarmManager
    SetAlarmRepeat(navController, alarmManager)
}