package com.example.alarmapp.view.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.model.AlarmGroupState
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.IconToggleButton_

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.groupedAlarmItems(
    alarms: List<AlarmState>,
    alarmGroup: AlarmGroupState,
    mainViewModel: MainViewModel,
    navController: NavController,
    is24HourView: Boolean
) {
    alarmGroupStickyHeader(alarmGroup = alarmGroup, mainViewModel, navController)
    if (alarmGroup.isFolded) {
        foldedAlarmGroupItems(alarms, alarmGroup, mainViewModel, navController, is24HourView)
    } else {
        expandedAlarmGroupItems(alarms, alarmGroup, mainViewModel, navController, is24HourView)
    }
    stickyHeader {}
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.alarmGroupStickyHeader(
    alarmGroup: AlarmGroupState,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    stickyHeader(key = alarmGroup.groupName) {
        var menuExpanded by remember {
            mutableStateOf(false)
        }

        val alarmInGroup = mainViewModel.getAlarmInGroup(alarmGroup.groupName).values
        val containsOnAlarm = alarmInGroup.any { it.isOn }
        Box(modifier =Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .animateItem(fadeInSpec = null, fadeOutSpec = null)
            .clickable { alarmGroup.isFolded = !alarmGroup.isFolded }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 14.dp)
            ) {
                FoldButton(
                    isFolded = alarmGroup.isFolded,
                    onFoldedChange = { alarmGroup.isFolded = it })
                Text(
                    text = alarmGroup.groupName,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp).weight(1f)
                )
                IconButton(onClick = {
                    navController.navigate(
                        Routes.CreateAlarmInGroup.slottedRoute!!.format(
                            alarmGroup.groupName
                        )
                    )
                }) {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = "그룹에 알람 추가",
                    )
                }
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            Icons.Outlined.MoreVert,
                            contentDescription = "그룹 메뉴",
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },

                        ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = if (containsOnAlarm) "알람 모두 끄기" else "알람 모두 켜기")
                            },
                            onClick = {
                                alarmInGroup.map { it.isOn = !containsOnAlarm }
                            })

                        HorizontalDivider()
                        DropdownMenuItem(
                            text = {
                                Text(text = "그룹 삭제")
                            },
                            onClick = { mainViewModel.deleteAlarmGroup(alarmGroup) })
                    }
                }
            }
        }
    }
}

fun LazyListScope.foldedAlarmGroupItems(
    alarms: List<AlarmState>,
    groupState: AlarmGroupState,
    mainViewModel: MainViewModel,
    navController: NavController,
    is24HourView: Boolean
) {
    item(key = groupState.groupName.hashCode()) {
        Modifier
            .fillMaxWidth()
        LazyRow(
            contentPadding = PaddingValues(start = 32.dp, end = 8.dp, top = 8.dp, bottom = 16.dp),
            modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
        ) {
            items(alarms, key = { it.id }) { alarm ->
                AlarmItemView(
                    alarm = alarm,
                    alarmGroup = groupState,
                    mainViewModel = mainViewModel,
                    navController = navController,
                    modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                    is24HourView = is24HourView
                )
            }
        }
    }
}

fun LazyListScope.expandedAlarmGroupItems(
    alarms: List<AlarmState>,
    alarmGroup: AlarmGroupState,
    alarmViewModel: MainViewModel,
    navController: NavController,
    is24HourView: Boolean
) {
    itemsIndexed(alarms, key = { _, alarm -> alarm.id }) { i, alarm ->
        Box(
            modifier = Modifier
                .animateItem(fadeInSpec = null, fadeOutSpec = null)
                .padding(horizontal = 32.dp)
                .padding(top = 8.dp)
                .padding(bottom = if (i == alarms.lastIndex) 16.dp else 0.dp)
        ) {
            AlarmItemView(
                alarm = alarm,
                alarmGroup = alarmGroup,
                mainViewModel = alarmViewModel,
                navController = navController,
                modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                is24HourView = is24HourView
            )
        }
    }
}


@Composable
fun FoldButton(isFolded: Boolean, onFoldedChange: (Boolean) -> Unit) {
    val isExpanded = !isFolded
    val painter = if (isExpanded) {
        painterResource(id = R.drawable.baseline_unfold_less_24)
    } else {
        painterResource(id = R.drawable.baseline_unfold_more_24)
    }
    IconToggleButton_(
        painter = painter,
        checked = isExpanded,
        onCheckedChange = { onFoldedChange(!it) }
    )
}