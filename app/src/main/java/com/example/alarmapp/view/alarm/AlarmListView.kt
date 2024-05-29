package com.example.alarmapp.view.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.model.AlarmViewModel
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


@Preview()
@Composable
fun AlarmListViewPreview() {
    val alarms = LinkedList<Alarm>()

    alarms.add(Alarm(1, "", 16, 42, true, true, ""))
    alarms.add(Alarm(2, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(3, "", 5, 42, true, false, ""))
    alarms.add(Alarm(4, "", 3, 42, false, false, ""))
    alarms.add(Alarm(5, "", 11, 42, true, true, "테스트 그룹 1"))
    alarms.add(Alarm(6, "test2", 6, 42, false, true, "테스트 그룹 1"))
    alarms.add(Alarm(7, "", 3, 42, true, false, "테스트 그룹 1"))
    alarms.add(Alarm(8, "test3", 7, 42, false, false, "테스트 그룹 222"))
    alarms.add(Alarm(9, "test4", 7, 42, false, false, "not existing gorup name"))
    alarms.add(Alarm(10, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(11, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(12, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(13, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(14, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(15, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(16, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(17, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(18, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(19, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(20, "test", 14, 42, false, true, ""))
    alarms.add(Alarm(21, "test", 14, 42, false, true, ""))

    val alarmGroups = mutableMapOf<String, AlarmGroup>()

    alarmGroups["테스트 그룹 1"] = AlarmGroup("테스트 그룹 1", mutableListOf(alarms[4], alarms[5], alarms[6]))
    alarmGroups["테스트 그룹 222"] = AlarmGroup("테스트 그룹 222", mutableListOf(alarms[7]))

    AlarmListView(alarms, alarmGroups)
}
