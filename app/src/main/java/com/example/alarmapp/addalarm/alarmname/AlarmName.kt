package com.example.alarmapp.addalarm.alarmname

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.example.alarmapp.alarmdata.AlarmViewModel

@Composable
fun AlarmName(alarmViewModel: AlarmViewModel ) {
    val alarmName = if(alarmViewModel.flag==1){
        remember { mutableStateOf("") }
    } else {
        remember { mutableStateOf(alarmViewModel.getAlarmName()) }
    }
    OutlinedTextField(
        value = alarmName.value,
        onValueChange = {alarmName.value = it},
        textStyle = TextStyle(color=Color.Black, fontSize =  18.sp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        label = { Text(text = "알람 이름")},
        modifier = Modifier
            .fillMaxWidth()
    )
    alarmViewModel.setAlarmName(alarmName.value)
}