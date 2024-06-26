package com.example.alarmapp.view.alarm

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmListView(
    navController: NavController,
    mainViewModel: MainViewModel,
    innerPadding: PaddingValues,
    is24HourView: Boolean,
    sortedAlarms: List<AlarmState>
) {
    val lazyListState = rememberLazyListState()

    val alarmGroups = remember {
        mainViewModel.alarmGroupStateMap
    }

    val selectedRepeatFilters = mainViewModel.selectedRepeatFiltersIndex
    val selectedGroupFilters = mainViewModel.selectedGroupFilters
    val selectedFilterSetNames = mainViewModel.selectedFilterSet

    val alarmList = getFilteredAlarms(
        sortedAlarms,
        selectedRepeatFilters,
        selectedGroupFilters,
        selectedFilterSetNames,
        mainViewModel
    )

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(innerPadding)
    ) {
        val insertedGroup = LinkedHashSet<String>()

        for (alarm in alarmList) {
            if (
                alarm.groupName != "" &&
                alarmGroups[alarm.groupName] != null &&
                !insertedGroup.contains(alarm.groupName)
            ) {
                insertedGroup.add(alarm.groupName)
                groupedAlarmItems(
                    alarms = alarmList.filter { it.groupName == alarm.groupName },
                    alarmGroup = alarmGroups[alarm.groupName]!!,
                    mainViewModel = mainViewModel,
                    navController = navController,
                    is24HourView = is24HourView
                )
            } else if (!insertedGroup.contains(alarm.groupName)) {
                item(key = alarm.id) {
                    AlarmItemView(
                        alarm = alarm,
                        mainViewModel = mainViewModel,
                        navController = navController,
                        modifier = Modifier.animateItemPlacement(),
                        is24HourView = is24HourView
                    )
                }

            }
        }
    }
}

fun getFilteredAlarms(
    sortedAlarms: List<AlarmState>,
    selectedRepeatFilters: List<Int>,
    selectedGroupFilters: List<String>,
    selectedFilterSetNames: List<String>,
    mainViewModel: MainViewModel
): List<AlarmState> {
    Log.d("test", sortedAlarms.toString())
    return if (selectedGroupFilters.isEmpty() && selectedRepeatFilters.isEmpty() && selectedFilterSetNames.isEmpty()) {
        sortedAlarms
    } else {
        sortedAlarms.filter { alarm ->
            val groupFilterCondition =
                selectedGroupFilters.isNotEmpty() && selectedGroupFilters.contains(alarm.groupName)
            val repeatFilterCondition =
                selectedRepeatFilters.isNotEmpty() && selectedRepeatFilters.any { alarm.repeatOnWeekdays[it] }
            val filterSetCondition = if (selectedFilterSetNames.isNotEmpty()) {
                val selectedFilterSets = selectedFilterSetNames.mapNotNull { mainViewModel.getFilterByName(it) }
                selectedFilterSets.any { selectedFilterSet ->
//                    Log.d("test1",  selectedFilterSet.name)
//                    Log.d("test1",  alarm.name)
//                    Log.d("test1",  selectedFilterSet.repeatFilter.toString())
//                    Log.d("test1", alarm.repeatOnWeekdays.toString())
//                    Log.d("test1",
//                        (alarm.repeatOnWeekdays.toList() == selectedFilterSet.repeatFilter.toList()).toString())

                    val isEmpty = selectedFilterSet.repeatFilter.size == 7 && selectedFilterSet.repeatFilter.all { !it }
                    (selectedFilterSet.groupFilter.isEmpty() && isEmpty) ||
                            (selectedFilterSet.groupFilter.isNotEmpty() && isEmpty && selectedFilterSet.groupFilter.contains(
                                alarm.groupName
                            )) ||
                            (selectedFilterSet.groupFilter.isEmpty() && !isEmpty && alarm.repeatOnWeekdays.toList() == selectedFilterSet.repeatFilter.toList()) ||
                            (selectedFilterSet.groupFilter.isNotEmpty() && !isEmpty &&
                                    selectedFilterSet.groupFilter.contains(alarm.groupName) && alarm.repeatOnWeekdays.toList() == selectedFilterSet.repeatFilter.toList())
                }
            } else {
                false
            }
            groupFilterCondition || repeatFilterCondition || filterSetCondition
        }
    }
}

