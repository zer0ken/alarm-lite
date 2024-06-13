package com.example.alarmapp.view.alarm

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.model.AlarmGroupState
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmItemView(
    alarm: AlarmState,
    alarmGroup: AlarmGroupState? = null,
    mainViewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier,
) {
    var cardShape = CardDefaults.shape

    var enabled by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(
        label = alarm.id.toString(),
        targetValue = if (enabled) 1.0f else 0.0f,
        animationSpec = TweenSpec(
            durationMillis = 200
        )
    )

    var cardModifier: Modifier = modifier
        .clip(cardShape)
        .combinedClickable(
            onClick = {
                if (mainViewModel.isSelectMode) {
                    alarm.isSelected = !alarm.isSelected
                } else {
                    navController.navigate(
                        Routes.UpdateAlarm.slottedRoute?.format(alarm.id)
                            ?: Routes.CreateAlarm.route
                    )
                }
            },
            onLongClick = {
                if (!mainViewModel.isSelectMode) {
                    mainViewModel.isSelectMode = true
                }
            }
        )
        .graphicsLayer(alpha = alpha)

    var rowModifier: Modifier = Modifier
    var switchModifier: Modifier = Modifier

    var contentFontSize: TextUnit
    var timeFontSize: TextUnit

    if (mainViewModel.isSelectMode && !alarm.isSelected) {
        cardShape = CardDefaults.outlinedShape
    }

    if (alarmGroup?.isFolded == true) {
        cardModifier = cardModifier
            .height(76.dp)
        rowModifier = rowModifier
            .padding(end = 14.dp, top = 4.dp, bottom = 4.dp)
        switchModifier = switchModifier
            .scale(0.6f)
            .size(40.dp)

        contentFontSize = 14.sp
        timeFontSize = 30.sp
    } else {
        cardModifier = cardModifier.fillMaxWidth()
        rowModifier = rowModifier
            .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
        switchModifier = switchModifier
            .scale(0.8f)

        contentFontSize = 14.sp
        timeFontSize = 34.sp
    }

    LaunchedEffect(enabled) {
        enabled = true
    }

    Card(
        shape = cardShape,
        modifier = cardModifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            if (mainViewModel.isSelectMode) {
                Checkbox(checked = alarm.isSelected, onCheckedChange = { alarm.isSelected = it })
            } else {
                Spacer(modifier = Modifier.width(22.dp))
            }

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                if (alarm.name != "") {
                    Text(text = alarm.name, fontSize = contentFontSize)
                }
                Text(
                    text = "${alarm.hour} : ${alarm.minute}",
                    fontSize = timeFontSize
                )
                if (alarm.repeatOnWeekdays.any { it }) {
                    val weekdays = arrayOf("일", "월", "화", "수", "목", "금", "토")
                    var repeatOn = "반복:"
                    repeat(7) { idx ->
                        if (alarm.repeatOnWeekdays[idx]) {
                            repeatOn += " " + weekdays[idx]
                        }
                    }
                    Text(text = repeatOn, fontSize = contentFontSize)
                }
            }
            Spacer(modifier = Modifier.weight(weight = 1.0f))
            Switch(
                checked = alarm.isOn,
                modifier = switchModifier,
                onCheckedChange = {
                    alarm.isOn = it
                    mainViewModel.updateAlarm(alarm)
                }
            )
        }
    }
    Spacer(
        modifier = Modifier
            .width(14.dp)
            .height(14.dp)
    )
}