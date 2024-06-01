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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.model.AlarmViewModel

/**
 * 리스트 내에서 알람 하나를 표시하는 컴포저블 함수입니다.
 *
 * @param alarm 표시할 알람 정보
 * @param alarmViewModel AlarmViewModel 인스턴스
 * @author 이현령
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmItemView(
    alarm: Alarm,
    alarmViewModel: AlarmViewModel,
    modifier: Modifier
) {
    val alarmState = alarmViewModel.getAlarmState(alarm.id)
    val groupState = alarmViewModel.getAlarmGroupState(alarm.groupName)
    val setSelected = { isSelected: Boolean -> alarmState.isSelected = isSelected }

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
                if (alarmViewModel.isSelectMode) {
                    alarmState.isSelected = !alarmState.isSelected
                }
            }, onLongClick = {
                if (!alarmViewModel.isSelectMode) {
                    alarmViewModel.isSelectMode = true
                }
            }
        )
        .graphicsLayer(alpha = alpha)

    var rowModifier: Modifier = Modifier
    var switchModifier: Modifier = Modifier

    var contentFontSize: TextUnit
    var timeFontSize: TextUnit

    if (alarmViewModel.isSelectMode && !alarmState.isSelected) {
        cardShape = CardDefaults.outlinedShape
    }

    if (groupState.isFolded) {
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
            if (alarmViewModel.isSelectMode) {
                Checkbox(checked = alarmState.isSelected, onCheckedChange = setSelected)
            } else {
                Spacer(modifier = Modifier.width(22.dp))
            }

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                if (alarm.content != "") {
                    Text(text = alarm.content, fontSize = contentFontSize)
                }
                Text(
                    text = "${alarm.hour} : ${alarm.minute}",
                    fontSize = timeFontSize
                )
            }
            Spacer(modifier = Modifier.weight(weight = 1.0f))
            Switch(
                checked = alarm.isOn,
                modifier = switchModifier,
                onCheckedChange = { /*TODO*/ }
            )
        }
    }
    Spacer(
        modifier = Modifier
            .width(14.dp)
            .height(14.dp)
    )
}

@Preview (showBackground = true)
@Composable
fun PrevAlarmItemView() {
    val alarm =Alarm("Morning Alarm", 7, 30, remember { mutableStateListOf(false, false, false, false, false, false, false) }, updatedTime = System.currentTimeMillis())
    val alarmViewModel = AlarmViewModel()
    AlarmItemView(alarm, alarmViewModel, Modifier)
}