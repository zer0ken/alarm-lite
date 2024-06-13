package com.example.alarmapp.view.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.model.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmListView(
    navController: NavController,
    mainViewModel: MainViewModel,
    innerPadding: PaddingValues
) {
    val lazyListState = rememberLazyListState()

    val alarms = remember {
        mainViewModel.alarmStateMap
    }
    val alarmGroups = remember {
        mainViewModel.alarmGroupStateMap
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
    ) {
        val insertedGroup = LinkedHashSet<String>()
        for (alarm in alarms.values) {
            if (
                alarm.groupName != "" &&
                alarmGroups[alarm.groupName] != null &&
                !insertedGroup.contains(alarm.groupName)
            ) {
                insertedGroup.add(alarm.groupName)
                groupedAlarmItems(
                    alarms = alarms.values.filter { it.groupName == alarm.groupName },
                    alarmGroup = alarmGroups[alarm.groupName]!!,
                    mainViewModel = mainViewModel,
                    navController = navController
                )
            } else if (!insertedGroup.contains(alarm.groupName)) {
                item(key = alarm.id) {
                    AlarmItemView(
                        alarm = alarm,
                        mainViewModel = mainViewModel,
                        navController = navController,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}
