package com.example.alarmapp.view.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.ui.theme.background

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmListView(
    navController: NavController,
    mainViewModel: MainViewModel,
    innerPadding : PaddingValues
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        contentPadding = innerPadding, //PaddingValues(vertical = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background)
    ) {
        val insertedGroup = LinkedHashSet<String>()
        for (alarm in mainViewModel.alarmStateMap.values) {
            if (
                alarm.groupName != "" &&
                mainViewModel.alarmGroupStateMap[alarm.groupName] != null &&
                !insertedGroup.contains(alarm.groupName)
            ) {
                insertedGroup.add(alarm.groupName)
                groupedAlarmItems(
                    alarms = mainViewModel.alarmStateMap.values.filter { it.groupName == alarm.groupName },
                    alarmGroup = mainViewModel.alarmGroupStateMap[alarm.groupName]!!,
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
