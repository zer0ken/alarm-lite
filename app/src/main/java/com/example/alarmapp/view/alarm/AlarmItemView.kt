package com.example.alarmapp.view.alarm

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
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
    is24HourView: Boolean
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
                if (mainViewModel.isSelectMode) {
                    mainViewModel.clearAllSelections()
                    mainViewModel.isSelectMode = false
                } else {
                    alarm.isSelected = true
                    mainViewModel.isSelectMode = true
                }
            }
        )
        .graphicsLayer(alpha = alpha)

    val rowModifier: Modifier = Modifier
        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
        .fillMaxWidth()
    val switchModifier: Modifier = Modifier
        .scale(0.6f)
        .size(40.dp)
    var contentFontSize: TextUnit = 14.sp
    var timeFontSize: TextUnit = 34.sp

    if (alarmGroup == null) {
        cardModifier = cardModifier.fillMaxWidth()
    } else if (alarmGroup.isFolded) {
//        cardModifier = cardModifier.widthIn(min = 200.dp, max = 300.dp)
        contentFontSize = 10.sp
        timeFontSize = 26.sp
    } else {
        cardModifier = cardModifier.fillMaxWidth()
    }

    var specifiedDateRange = alarm.getSpecifiedDateRange()
    if (specifiedDateRange != null && alarmGroup != null) {
        specifiedDateRange = specifiedDateRange.replace(" ~ ", "\n     ~ ")
    }

    LaunchedEffect(enabled) {
        enabled = true
    }

    Card(
        shape = cardShape,
        modifier = cardModifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            if (mainViewModel.isSelectMode) {
                Checkbox(checked = alarm.isSelected, onCheckedChange = { alarm.isSelected = it })
            } else {
                Spacer(modifier = Modifier.width(22.dp))
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .widthIn(max = 150.dp)
            ) {
                if (alarm.name != "") {
                    Text(text = alarm.name, fontSize = contentFontSize)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val formattedTime =
                        mainViewModel.formatTime(alarm.hour, alarm.minute, is24HourView)
                    Text(
                        text = formattedTime,
                        fontSize = timeFontSize
                    )
                    if (alarm.isBookmarked) {
                        Icon(Icons.Filled.Star, "즐겨찾기")
                    }
                }
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
                if (specifiedDateRange != null) {
                    Text(text = specifiedDateRange, fontSize = contentFontSize)
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Switch(
                checked = alarm.isOn,
                onCheckedChange = {
                    alarm.isOn = it
                    mainViewModel.updateAlarm(alarm)
                },
                modifier = switchModifier
            )
        }
    }
    Spacer(
        modifier = Modifier
            .width(14.dp)
            .height(14.dp)
    )
}
