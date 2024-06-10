package com.example.alarmapp.addalarm.vibrator

import android.content.Context
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
import com.example.alarmapp.R
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun Vibrator(context:Context, alarmViewModel: AlarmViewModel) {
    val vibrationIsOn = remember { mutableStateOf(true)}
    val showVibrationDialog = remember{ mutableStateOf(false) }
    val selectedVibrationPattern = remember{ mutableStateOf("무음") }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showVibrationDialog.value = !showVibrationDialog.value }
    ) {
        Column {
            Text(text = stringResource(id = R.string.vibration))
            Text(
                text = selectedVibrationPattern.value,
                color = Color(0xFF734D4D),
                fontSize = 12.sp
            )
        }
        Switch(
            checked = vibrationIsOn.value,
            onCheckedChange = {vibrationIsOn.value = !vibrationIsOn.value},
            modifier = Modifier
                .scale(0.6f)
        )
    }
    if (showVibrationDialog.value) {
        VibratorPatternDialog(
            context = context,
            onDismiss = { showVibrationDialog.value = false },
            onPatternSelected = { pattern ->
                selectedVibrationPattern.value = pattern
                showVibrationDialog.value = false
                alarmViewModel.setVibrationPattern(selectedVibrationPattern.value)
            }
        )
    }
    alarmViewModel.setVibrate(vibrationIsOn.value)
    alarmViewModel.setSelectedVibrationPattern(selectedVibrationPattern.value)
}