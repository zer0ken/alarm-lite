package com.example.alarmapp.view.bottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.alarm.getFilteredAlarms

@Composable
fun EditBottomBar(mainViewModel: MainViewModel) {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    var selected by remember { mutableStateOf(false) }
    val selectedText = if (!selected) "전체 선택" else "전체 해제"

    var onOff by remember { mutableStateOf(false) }
    val onOffText = if (!onOff) "끄기" else "켜기"

    val alarmGroups = remember {
        mainViewModel.alarmGroupStateMap.values.toList()
    }

    LaunchedEffect(mainViewModel.selectedSort, mainViewModel.alarmStateMap) {
        mainViewModel.updateSortedAlarms()
    }

    val alarmList = getFilteredAlarms(
        sortedAlarms = mainViewModel.sortedAlarms,
        selectedRepeatFilters = mainViewModel.selectedRepeatFiltersIndex,
        selectedGroupFilters = mainViewModel.selectedGroupFilters,
        selectedFilterSetNames = mainViewModel.selectedFilterSet,
        mainViewModel
    )

    BottomAppBar(
        modifier = Modifier.height(60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    selected = !selected
                    mainViewModel.toggleSelectAll(alarmList, selected)
                }
            ) {
                Text(text = selectedText)
            }
            TextButton(
                onClick = {
                    onOff = !onOff
                    mainViewModel.onOffSelectedAlarms(alarmList, onOff)
                }) {
                Text(text = onOffText)
            }
            TextButton(onClick = {
                isDropdownMenuExpanded = true
            }) {
                Text(text = "그룹화")
            }
            DropdownMenu(
                expanded = isDropdownMenuExpanded,
                onDismissRequest = { isDropdownMenuExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(300.dp),
            ) {
                alarmGroups.map { it.groupName }.forEach {
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = it,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        },
                        onClick = {
                            mainViewModel.updateGroupForSelectedAlarms(alarmList, it)
                            isDropdownMenuExpanded = false
                            mainViewModel.isSelectMode = false
                        }
                    )
                }
            }
            TextButton(onClick = {
                mainViewModel.updateGroupForSelectedAlarms(alarmList, "")
                mainViewModel.isSelectMode = false
            }) {
                Text(text = "그룹 해제")
            }
            TextButton(onClick = {
                mainViewModel.deleteSelectedAlarms(alarmList)
                mainViewModel.isSelectMode = false
            }) {
                Text(text = "삭제")
            }
        }
    }
}