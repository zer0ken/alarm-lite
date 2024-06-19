package com.example.alarmapp.view.bottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.alarm.getFilteredAlarms

@Composable
fun EditBottomBar(mainViewModel: MainViewModel) {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var setGroupName by remember {
        mutableStateOf("")
    }

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

    val dropdownMenuOffset = remember { mutableStateOf(Offset.Zero) }

    BottomAppBar(
        modifier = Modifier.height(60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
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
            Box {
                TextButton(onClick = {
                    isDropdownMenuExpanded = true
                },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        dropdownMenuOffset.value = coordinates.positionInWindow()
                    }
                ) {
                    Text(text = "그룹화")
                }
                DropdownMenu(
                    expanded = isDropdownMenuExpanded,
                    onDismissRequest = { isDropdownMenuExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .heightIn(max = 250.dp)
                ) {
                    var count = 0
                    if (alarmGroups.isNotEmpty()) {
                        alarmGroups.map { it.groupName }.forEach {
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Text(text = it)
                                    }
                                },
                                onClick = {
                                    mainViewModel.updateGroupForSelectedAlarms(alarmList, it)
                                    isDropdownMenuExpanded = false
                                    mainViewModel.isSelectMode = false
                                }
                            )
                            count++
                        }
                    }
                    if (count > 0) {
                        HorizontalDivider()
                    }
                    DropdownMenuItem(
                        text = {
                            Row {
                                Text(
                                    text = "그룹 생성",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        onClick = {
                            showDialog = true
                        }
                    )
                }
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    title = {
                        Text(text = "그룹 생성")
                    },
                    text = {
                        OutlinedTextField(
                            value = setGroupName,
                            onValueChange = { setGroupName = it },
                            label = { Text("그룹 이름") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                                mainViewModel.createGroupForSelectedAlarms(alarmList, setGroupName)
                                mainViewModel.isSelectMode = false
                            }
                        ) {
                            Text("생성")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text("취소")
                        }
                    }
                )
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