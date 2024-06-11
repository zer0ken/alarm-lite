package com.example.alarmapp.addalarm.ringagain

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun RingAgain(navController:NavController , alarmViewModel: AlarmViewModel) {
    val ringAgainIsOn = remember { mutableStateOf(true) }
    val selectedRingAgain = remember { mutableStateOf("") }

    if (alarmViewModel.flag == 2) {
        ringAgainIsOn.value = alarmViewModel.getRingAgain()
    }

    val gap = when(alarmViewModel.getRepeatGap()){
        5 -> "5분"
        10-> "10분"
        15-> "15분"
        else -> "30분"
    }

    val repeat = when(alarmViewModel.getRepeatNumber()){
        3 -> "3회"
        5-> "5회"
        else -> "계속 반복"
    }
    selectedRingAgain.value = "$gap, $repeat"

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Routes.SetAlarmRepeat.route)
            }
    ) {
        Column {
            Text(text = stringResource(id = R.string.ring_again))
            Text(
                text = selectedRingAgain.value,
                color = Color(0xFF734D4D),
                fontSize = 12.sp
            )
        }
        Switch(
            checked = ringAgainIsOn.value,
            onCheckedChange = {
                alarmViewModel.setRingAgain(!ringAgainIsOn.value)
                ringAgainIsOn.value = alarmViewModel.getRingAgain()
            },
            modifier = Modifier
                .scale(0.6f)
        )
    }
    alarmViewModel.setSelectedRingtone(selectedRingAgain.value)
}