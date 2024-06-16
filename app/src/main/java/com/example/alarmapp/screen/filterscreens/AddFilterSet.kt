package com.example.alarmapplication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.Filter
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.model.rememberAlarmState
import com.example.alarmapp.model.rememberFilter
import com.example.alarmapp.view.FilterTopAppBar
import java.time.DayOfWeek

@Composable
fun AddFilterSetScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    filter: Filter? = null
) {
    val scrollState = rememberScrollState()
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    val definedRepeatFilters = mainViewModel.definedRepeatFilters

//    var filterSetName = mainViewModel.filterSetName
//    var filterSetRepeatFilter = mainViewModel.filterSetRepeatFilter
//    var filterSetGroupFilter = mainViewModel.filterSetGroupFilter

    var filterSetName by remember {
        mutableStateOf(filter?.name ?: mainViewModel.filterSetName)
    }
    var filterSetRepeatFilter by remember {
        mutableStateOf(filter?.repeatFilter ?: mainViewModel.filterSetRepeatFilter)
    }
    val filterSetGroupFilter by remember {
        mutableStateOf(filter?.groupFilter ?: mainViewModel.filterSetGroupFilter)
    }

//    // 필터 설정 초기화 함수
//    fun clearFilterSet() {
//        filterSetName = ""
//        filterSetRepeatFilter = mutableListOf(false, false, false, false, false, false, false)
//        filterSetGroupFilter = mutableListOf()
//    }

    Log.d("test1", filterSetName)
    Log.d("test2", filterSetRepeatFilter.toString())
    Log.d("test3", filterSetGroupFilter.toString())

    Scaffold(
        topBar = {
            FilterTopAppBar("필터 셋 작성") {
                if (it) {
                    mainViewModel.updateFilter(
                        Filter(
                            name = filterSetName,
                            repeatFilter = filterSetRepeatFilter,
                            groupFilter = filterSetGroupFilter
                        )
                    )
                }
//                clearFilterSet()
                navController.navigate(Routes.MainScreen.route)
            }
        }
    ) { PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues)
                .verticalScroll(scrollState)
        ) {
            OutlinedTextField(
                value = filterSetName,
                onValueChange = { filterSetName = it },
                label = { Text("필터 이름") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (filterSetRepeatFilter.any { it }) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            mainViewModel.filterSetName = filterSetName
                            navController.navigate(Routes.RepeatFilterLabel.route)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = "반복 필터",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        val selectedDays = mutableListOf<String>()
                        filterSetRepeatFilter.forEachIndexed { index, isSelected ->
                            if (isSelected) {
                                selectedDays.add(definedRepeatFilters[index][0].toString())
                            }
                        }
                        Text(
                            text = "매주 ${selectedDays.joinToString(", ")}에 반복되는 알람",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        filterSetRepeatFilter = mainViewModel.defaultFilterSetRepeatFilter
                        mainViewModel.filterSetRepeatFilter = filterSetRepeatFilter
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "delete",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            if (filterSetGroupFilter.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = "그룹 필터",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "${filterSetGroupFilter.joinToString(", ")} 에 포함되는 알람",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        filterSetGroupFilter.clear()
                        mainViewModel.filterSetGroupFilter.clear()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "delete",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = { isDropDownMenuExpanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "필터 추가")
            }
            DropdownMenu(
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false },
            ) {
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "반복 필터"
                            )
                        }
                    },
                    onClick = {
                        mainViewModel.filterSetName = filterSetName
                        navController.navigate(Routes.RepeatFilterLabel.route)
                    }
                )
                Divider()
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "그룹 필터"
                            )
                        }
                    },
                    onClick = {
                        navController.navigate(Routes.GroupFilterLabel.route)
                    }
                )
            }
        }
    }
}