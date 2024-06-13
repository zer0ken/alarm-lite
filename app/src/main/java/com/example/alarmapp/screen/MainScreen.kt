package com.example.alarmapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.alarm.AlarmListView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val sortList = listOf<String>("정렬 방식 1", "정렬 2", "정렬 방식 3") //시스템에서 설정된 정렬 방식으로 정렬 진행
    var selectedSort by remember { mutableStateOf(sortList[0]) }
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "1시간 23분 뒤에 알람이 울립니다.",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {menuExpanded = true}) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
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
                                    Row (
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = sortItem,
                                            color = if(sortItem == selectedSort) Color(0xFF734D4D) else Color.Black
                                        )
                                        if (sortItem == selectedSort){
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
                                    selectedSort = sortItem
                                    menuExpanded = false
                                    // 정렬 방식 변경으로 인한 알람들 나열 작업
                                }
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /* 김종권 작성 */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.rounded_undo_24),
                            contentDescription = "Undo",
                        )
                    }
                    IconButton(onClick = { /* 김종권 작성 */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.rounded_redo_24),
                            contentDescription = "Redo",
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Routes.CreateAlarm.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Alarm"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.Setting.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Setting"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        AlarmListView(navController, mainViewModel, innerPadding)
    }
}