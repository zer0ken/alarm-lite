package com.example.alarmapp.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.alarmapp.R
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.ui.theme.Purple40

@Composable
fun AlarmSoundPicker(context: Context, alarmState: AlarmState, modifier: Modifier = Modifier) {
    val selectedRingtone = remember { mutableStateOf("") }

    selectedRingtone.value = if (alarmState.selectedRingtoneUri != null) {
        RingtoneManager.getRingtone(context, alarmState.selectedRingtoneUri).getTitle(context)
    } else {
        context.getString(R.string.nothing_selected)
    }

    val ringtonePickerIntent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
        putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
        putExtra(
            RingtoneManager.EXTRA_RINGTONE_TITLE,
            stringResource(id = R.string.select_alarm_sound)
        )
        putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, alarmState.selectedRingtoneUri)
    }

    val ringtoneLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? =
                    result.data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                alarmState.selectedRingtoneUri = uri
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { ringtoneLauncher.launch(ringtonePickerIntent) }
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            Column {
                Text(text = stringResource(id = R.string.alarm_sound))
                if (alarmState.isRingtoneOn) {
                    Text(
                        text = selectedRingtone.value,
                        color = Purple40
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Switch(
                checked = alarmState.isRingtoneOn,
                onCheckedChange = {
                    alarmState.isRingtoneOn = it
                    if (it && alarmState.selectedRingtoneUri == null) {
                        ringtoneLauncher.launch(ringtonePickerIntent)
                    }
                },
                modifier = Modifier
                    .size(10.dp)
                    .scale(0.6f)
                    .padding(end = 12.dp)
            )
        }
    }

}