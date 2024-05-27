package com.example.alarmapp.view

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmapp.R
import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.ui.theme.background
import java.util.LinkedList

/**
 * 알람 목록을 표시하는 컴포저블 함수입니다.
 *
 * 내부에 개별 알람 뿐만 아니라 알람 그룹 또한 표시해야 하므로 이에 대한 표시 방식도 이 함수에서 정의됩니다.
 *
 * @param alarms 표시하고자 하는 알람이 담긴 List
 * @param alarmGroups 알람 그룹의 이름과 알람 그룹 객체를 대응시키는 Map
 * @author 이현령
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmListView(alarms: List<Alarm>, alarmGroups: Map<String, AlarmGroup>) {
    val lazyListState = rememberLazyListState()

    val insertedGroup = LinkedHashSet<String>()

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background)
    ) {
        for (alarm in alarms) {
            if (
                alarm.groupName != "" &&
                alarmGroups[alarm.groupName] != null &&
                !insertedGroup.contains(alarm.groupName)
            ) {
                insertedGroup.add(alarm.groupName)
                stickyHeader { AlarmGroupItemView(alarmGroup = alarmGroups[alarm.groupName]!!) }
                items(alarmGroups[alarm.groupName]!!.groupAlarmList) {
                    AlarmItemView(alarm = it, inGroup = true)
                }
                stickyHeader {}
            } else if (!insertedGroup.contains(alarm.groupName)) {
                item { AlarmItemView(alarm = alarm) }
            }
        }
    }
}

/**
 * 리스트 내에서 알람 하나를 표시하는 컴포저블 함수입니다.
 *
 * @param alarm 표시할 알람 정보
 * @param inGroup 그룹 내의 알람인지 아닌지
 * @param inRow TODO 접힌 그룹 내에서 가로로 표시되어야 하는지
 * @author 이현령
 */
@Composable
fun AlarmItemView(alarm: Alarm, inGroup: Boolean = false, inRow: Boolean = false) {
    Card(
        modifier = Modifier.padding(horizontal = if (inGroup) 8.dp else 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                if (alarm.content != "") {
                    Text(text = alarm.content, fontSize = 8.sp)
                }
                Text(
                    text = "${alarm.hour} : ${alarm.minute}",
                    fontSize = 16.sp
                )
            }
            Switch(
                checked = alarm.isOn,
                modifier = Modifier
                    .scale(0.4f)
                    .width(30.dp)
                    .height(30.dp),
                onCheckedChange = { /*TODO*/ }
            )
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}

/**
 * 알람 그룹의 stickyHeader 내에 들어갈 요소를 표시하는 컴포저블 함수입니다.
 *
 * @param alarmGroup 표시하고자 하는 알람 그룹의 정보
 * @author 이현령
 */
@Composable
fun AlarmGroupItemView(alarmGroup: AlarmGroup) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to background,
                    0.7f to background,
                    1.0f to Color.Transparent,
                    startY = 0.8f,
                )
            )
            .padding(horizontal = 10.dp, vertical = 2.dp),
    ) {
        Text(text = alarmGroup.groupName, fontSize = 8.sp, modifier = Modifier.padding(horizontal =4.dp))
        IconToggleButton_(painter = painterResource(id = R.drawable.baseline_unfold_more_24))
        Spacer(modifier = Modifier.weight(weight = 1.0f))
        IconToggleButton_(painter = painterResource(id = R.drawable.outline_notifications_off_24))
        IconToggleButton_(painter = painterResource(id = R.drawable.baseline_alarm_off_24))
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Preview
@Composable
private fun AlarmItemViewPreview() {
    Column(
        modifier = Modifier
            .width(200.dp)
            .height(380.dp)
    ) {
        AlarmItemView(Alarm("", 16, 42, true, true, ""))
        AlarmItemView(Alarm("test", 16, 42, true, true, ""), true)
    }
}

@Preview
@Composable
fun AlarmListViewPreview() {
    val alarms = LinkedList<Alarm>()

    alarms.add(Alarm("", 16, 42, true, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("", 5, 42, true, false, ""))
    alarms.add(Alarm("", 3, 42, false, false, ""))
    alarms.add(Alarm("", 11, 42, true, true, "g1"))
    alarms.add(Alarm("test2", 6, 42, false, true, "g1"))
    alarms.add(Alarm("", 3, 42, true, false, "g1"))
    alarms.add(Alarm("test3", 7, 42, false, false, "g2"))
    alarms.add(Alarm("test4", 7, 42, false, false, "not existing gorup name"))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))
    alarms.add(Alarm("test", 14, 42, false, true, ""))

    val alarmGroups = mutableMapOf<String, AlarmGroup>()

    alarmGroups["g1"] = AlarmGroup("g1", mutableListOf(alarms[4], alarms[5], alarms[6]))
    alarmGroups["g2"] = AlarmGroup("g2", mutableListOf(alarms[7]))

    Column(
        modifier = Modifier
            .width(200.dp)
            .height(380.dp)
    ) {
        AlarmListView(alarms, alarmGroups)
    }
}
