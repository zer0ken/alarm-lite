package com.example.alarmapp.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.model.AlarmComparator
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.alarm.AlarmListView
import com.example.alarmapp.view.bottomBar.DefaultBottomBar
import com.example.alarmapp.view.bottomBar.EditBottomBar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val sortList = listOf("시간순", "알림순")
    var menuExpanded by remember { mutableStateOf(false) }

    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    val alarms = mainViewModel.alarmStateMap.values.toList().sortedWith(AlarmComparator.ringFirst)
    LaunchedEffect(Unit) { mainViewModel.fetchAll() }
    var sortedAlarms by remember { mutableStateOf(alarms) }

    LaunchedEffect(alarms, mainViewModel.selectedSort) {
        sortedAlarms = if (mainViewModel.selectedSort == "시간순") {
            alarms.sortedWith(AlarmComparator.absolute)
        } else {
            alarms.sortedWith(AlarmComparator.relative)
        }
    }

    LaunchedEffect(mainViewModel.isSelectMode) {
        if (mainViewModel.isSelectMode) {
            scrollBehavior.state.heightOffset = -1000f
        }
    }

    var firstText by remember { mutableStateOf("") }
    var secondText by remember { mutableStateOf("") }
    if (alarms.isEmpty()) {
        firstText = "알람이 없습니다"
        secondText = "알람을 추가해 주세요"
    } else {
        val allAlarmsOff = alarms.all { !it.isOn }
        if (allAlarmsOff) {
            firstText = "모든 알람이\n꺼진 상태입니다"
            secondText = "알람을 켜주세요"
        } else {
            val currentTime = remember { LocalDateTime.now() }
            val alarmTime = alarms[0].getNextRingTime()
            val duration = java.time.Duration.between(currentTime, alarmTime)
            val hoursDifference = duration.toHours()
            val minutesDifference = duration.toMinutes()
            if (minutesDifference >= 24 * 60) {
                var temp = (7 + alarmTime.dayOfWeek.value - currentTime.dayOfWeek.value) % 7
                if (temp == 0) {
                    temp += 7
                }
                firstText = "${temp}일 후에\n알람이 울립니다"
            } else {
                firstText = if (minutesDifference % 60 == 59L) {
                    "${hoursDifference + 1}시간 0분 후에\n알람이 울립니다"
                } else {
                    "${hoursDifference}시간 ${(minutesDifference + 1) % 60}분 후에\n알람이 울립니다"
                }
            }
            val formatter = DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.forLanguageTag("ko"))
            val formattedTime = mainViewModel.formatTime(
                alarmTime.hour,
                alarmTime.minute,
                mainViewModel.is24HourView
            )
            secondText = "${alarmTime.format(formatter)} $formattedTime"
        }
    }

    BackHandler(mainViewModel.isSelectMode) { mainViewModel.isSelectMode = false }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (!mainViewModel.isSelectMode) {
                LargeTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    expandedHeight = 280.dp,
                    title = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = BiasAlignment.Horizontal(-scrollBehavior.state.collapsedFraction),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 24.dp)
                                .height(280.dp)
                        ) {
                            if (scrollBehavior.state.collapsedFraction < 0.5f) {
                                Text(
                                    text = firstText,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 34.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.alpha(
                                        abs(scrollBehavior.state.collapsedFraction - 0.5f) * 2
                                    )
                                )
                                Text(
                                    text = secondText,
                                    fontSize = 20.sp,
                                    modifier = Modifier.alpha(
                                        abs(scrollBehavior.state.collapsedFraction - 0.5f) * 2
                                    )
                                )
                            } else {
                                Text(
                                    text = "알람 목록",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    modifier = Modifier.alpha(
                                        abs(scrollBehavior.state.collapsedFraction - 0.5f) * 2
                                    )
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { menuExpanded = true }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_sort_24),
                                contentDescription = "Sort"
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            modifier = Modifier
                                .width(128.dp)
                        ) {
                            sortList.forEach { sortItem ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = sortItem,
                                                color = if (sortItem == mainViewModel.selectedSort) Color(
                                                    0xFF734D4D
                                                ) else Color.Black
                                            )
                                            if (sortItem == mainViewModel.selectedSort) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Selected sort",
                                                    tint = Color(0xFF734D4D),
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        mainViewModel.selectedSort = sortItem
                                        menuExpanded = false
                                        sortedAlarms = if (mainViewModel.selectedSort == "시간순") {
                                            alarms.sortedWith(AlarmComparator.absolute)
                                        } else {
                                            alarms.sortedWith(AlarmComparator.relative)
                                        }
                                    }
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Routes.CreateAlarm.route) {
                                popUpTo(Routes.MainScreen.route) { inclusive = false }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Alarm"
                            )
                        }
                        IconButton(onClick = {
                            navController.navigate(Routes.Setting.route) {
                                popUpTo(Routes.MainScreen.route) { inclusive = false }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Setting"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            } else {
                TopAppBar(
                    title = { Text(text = "${mainViewModel.getSelectedAlarms().size}개 선택됨") },
                    actions = {
                        IconButton(onClick = {
                            mainViewModel.getSelectedAlarms().values.forEach {
                                it.isSelected = false
                            }
                            mainViewModel.isSelectMode = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "선택 취소"
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (mainViewModel.isSelectMode) {
                EditBottomBar(mainViewModel)
            } else {
                DefaultBottomBar(navController, mainViewModel)
            }
        }
    ) { innerPadding ->
        AlarmListView(
            navController,
            mainViewModel,
            innerPadding,
            mainViewModel.is24HourView,
            sortedAlarms
        )
    }
}
