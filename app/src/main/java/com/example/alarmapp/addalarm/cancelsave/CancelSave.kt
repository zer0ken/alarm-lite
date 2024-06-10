package com.example.alarmapp.addalarm.cancelsave

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun CancelSave(context: Context, navController: NavController, alarmViewModel: AlarmViewModel) { //알람 정보 받아랑 알람 뷰모델 받아와서 알람 뷰모델에 기입 ㄱㄱ
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = { navController.navigate(Routes.MainScreen.route) },
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.cancel),
                color = Color.Black,
            )
        }
        TextButton(
            onClick = {
                alarmViewModel.makeAlarm(context)
                navController.navigate(Routes.MainScreen.route)
            },
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.save),
                color = Color.Black,
            )
        }
    }
}