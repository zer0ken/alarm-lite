package com.example.alarmapp.addalarm

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.alarmapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmName(alarmName: MutableState<String> ) {
    TextField(
        value = alarmName.value,
        onValueChange = {alarmName.value = it},
        placeholder = { Text(text = stringResource(id = R.string.alarm_name), color = Color.Gray)},
        colors = TextFieldDefaults.textFieldColors( containerColor = Color.White ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        //visualTransformation = , // 시발 어떻게 입력란 앞당기죠? 너무 불편한데
        modifier = Modifier
            .fillMaxWidth()
    )
}
