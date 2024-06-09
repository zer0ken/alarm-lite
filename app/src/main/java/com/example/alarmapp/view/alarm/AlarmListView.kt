package com.example.alarmapp.view.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.alarmdata.AlarmViewModel
import com.example.alarmapp.ui.theme.background

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
fun AlarmListView(
    alarms: List<Alarm>,
    alarmGroups: Map<String, AlarmGroup>,
) {
    val lazyListState = rememberLazyListState()

    val alarmViewModel: AlarmViewModel = viewModel()

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background)
    ) {
        val insertedGroup = LinkedHashSet<String>()
        for (alarm in alarms) {
            if (
                alarm.groupName != "" &&
                alarmGroups[alarm.groupName] != null &&
                !insertedGroup.contains(alarm.groupName)
            ) {
                insertedGroup.add(alarm.groupName)
                groupedAlarmItems(
                    alarms = alarms.filter { it.groupName == alarm.groupName },
                    alarmGroup = alarmGroups[alarm.groupName]!!,
                    alarmViewModel = alarmViewModel,
                )
            } else if (!insertedGroup.contains(alarm.groupName)) {
                item(key = alarm.id) {
                    AlarmItemView(
                        alarm = alarm,
                        alarmViewModel = alarmViewModel,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}
