package com.example.alarmapp.makegroupalarm.groupvibrator

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.alarmapp.R

@Composable
fun GroupVibrator(context: Context, vibrationIsOn: MutableState<Boolean>) {
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
        GroupVibratorPatternDialog(
            context = context,
            onDismiss = { showVibrationDialog.value = false },
            onPatternSelected = { pattern ->
                selectedVibrationPattern.value = pattern
                showVibrationDialog.value = false
            }
        )
    }
}