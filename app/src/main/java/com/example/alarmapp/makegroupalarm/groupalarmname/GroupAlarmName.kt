package com.example.alarmapp.makegroupalarm.groupalarmname

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp

@Composable
fun GroupAlarmName(alarmName: MutableState<String>) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember{ FocusRequester() }

    Box {
        BasicTextField(
            value = alarmName.value,
            onValueChange = { alarmName.value = it },
            textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
                .focusRequester(focusRequester)
        )

        if (!isFocused) {
            Text(
                text = "그룹 알람 이름",
                color = Color.Gray,
            )
        }
    }
    HorizontalDivider(color = Color.Black)
}
