package com.example.alarmapp.makegroupalarm.groupalarmrepeat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.alarmapp.R
import com.example.alarmapp.alarmdata.AlarmManager

@Composable
fun GroupUsingOrNot(alarmManager: AlarmManager) {
    val isOn = remember { mutableStateOf(true) }
    val usingOrNot = if (isOn.value) stringResource(id = R.string.using) else stringResource(id = R.string.not_using)
    val backgroundColor = if (isOn.value) Color.LightGray else Color.White
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 24.dp)
            .clickable { isOn.value = !isOn.value }
    ) {
        Text(text = usingOrNot)
        Switch(
            checked = isOn.value,
            onCheckedChange = {isOn.value = !isOn.value},
            modifier = Modifier
                .scale(0.6f)
        )
    }
}