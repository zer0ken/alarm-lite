package com.example.alarmapp.view

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
import com.example.alarmapp.R

@Composable
fun CancelSaveBottomBar(onSave: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = { onSave(false) },
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.cancel),
                color = Color.Black,
            )
        }
        TextButton(
            onClick = { onSave(true) },
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