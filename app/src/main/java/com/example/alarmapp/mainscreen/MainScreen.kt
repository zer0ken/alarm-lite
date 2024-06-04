package com.example.alarmapp.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.alarmdata.AlarmManager
import com.example.alarmapp.model.AlarmViewModel

@Composable
fun MainScreen(navController: NavController, alarmManager: AlarmManager, alarmViewModel: AlarmViewModel) {
    //알람 뷰모델이 필요함 뷰모델 리스트 받아와야댐
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffE9E9E9))
            .padding(horizontal = 24.dp)
    ) {
        ToolBar(navController)
        MainAlarmList(alarmManager, alarmViewModel)

        //그룹 생성 화면 이동 버튼
        Icon(
            imageVector = Icons.Default.Build,
            contentDescription = "임시 그룹 생성 화면 이동",
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.MakeGroupAlarm.route)
                }
        )
    }
}