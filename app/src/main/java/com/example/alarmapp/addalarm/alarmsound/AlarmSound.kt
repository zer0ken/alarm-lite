package com.example.alarmapp.addalarm.alarmsound

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
fun AlarmSound(ringtoneIsOn: MutableState<Boolean>, context: Context, selectedSoundUri: MutableState<Uri?>) {
    val selectedRingtone = remember{ mutableStateOf("")}
    selectedRingtone.value = if (selectedSoundUri.value != null){
        RingtoneManager.getRingtone(context, selectedSoundUri.value).getTitle(context)
    }
    else{
        context.getString(R.string.nothing_selected)
    }

    val ringtonePickerIntent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
        putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
        putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, stringResource(id = R.string.select_alarm_sound))
        putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, selectedSoundUri.value)
    }

    val ringtoneLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                selectedSoundUri.value = uri
            }
        }
    )

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { ringtoneLauncher.launch(ringtonePickerIntent) }
    ) {
        Column {
            Text(text =  stringResource(id = R.string.alarm_sound))
            Text(
                text = selectedRingtone.value,
                color = Color(0xFF734D4D),
                fontSize = 12.sp
            )
        }
        
        Switch(
            checked = ringtoneIsOn.value,
            onCheckedChange = {ringtoneIsOn.value = !ringtoneIsOn.value},
            modifier = Modifier
                .scale(0.6f)
        )
    }
}