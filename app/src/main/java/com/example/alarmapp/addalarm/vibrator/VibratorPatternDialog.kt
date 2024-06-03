package com.example.alarmapp.addalarm.vibrator

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.alarmapp.R

@Composable
fun VibratorPatternDialog(context: Context, onDismiss: () -> Unit, onPatternSelected: (String) -> Unit) {
    val options = listOf("무음", "Short", "Medium", "Basic Call")
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.vibration)) },
        confirmButton = {
            Column {
                options.forEach { option ->
                    TextButton(
                        onClick = {
                            onPatternSelected(option)
                            when (option) {
                                "Short" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100, 50, 100), -1))
                                "Medium" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 200, 100, 200), -1))
                                "Basic Call" -> vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 300, 150, 300), -1))
                                else -> vibrator.cancel()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = option)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = onDismiss) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }
            }
        },
    )
}