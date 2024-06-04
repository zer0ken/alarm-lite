package com.example.alarmapp.makegroupalarm.groupalarmrepeat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes

@Composable
fun GroupAlarmRepeat(repeatIsOn: MutableState<Boolean>, navController: NavController) {
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
                text = "추후에 뷰모델과 연결",
                color = Color(0xFF734D4D),
                fontSize = 12.sp
            )
        }
        Switch(
            checked = repeatIsOn.value,
            onCheckedChange = {repeatIsOn.value = !repeatIsOn.value},
            modifier = Modifier
                .scale(0.6f)
        )
    }
}