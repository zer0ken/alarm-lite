package com.example.alarmapp.screen.filterscreens

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.model.Filter
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.FilterTopAppBar

@Composable
fun AddFilterSetScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    filter: Filter? = null
) {
    val scrollState = rememberScrollState()

    val definedRepeatFilters = mainViewModel.definedRepeatFilters

    var filterSetName by remember {
        mutableStateOf(filter?.name ?: mainViewModel.filterSetName)
    }
    var filterSetRepeatFilter by remember {
        mutableStateOf(filter?.repeatFilter ?: mainViewModel.filterSetRepeatFilter)
    }
    val filterSetGroupFilter by remember {
        mutableStateOf(filter?.groupFilter ?: mainViewModel.filterSetGroupFilter)
    }

    var isFilterNameSet by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            FilterTopAppBar("필터 작성") {
                if (it) {
                    if (filterSetName.isNotBlank()) {
                        mainViewModel.updateFilter(
                            Filter(
                                name = filterSetName,
                                repeatFilter = filterSetRepeatFilter,
                                groupFilter = filterSetGroupFilter
                            )
                        )
                        navController.navigate(Routes.FilterSetListScreen.route) {
                            popUpTo("FilterSetListScreen") { inclusive = false }
                            launchSingleTop = true
                        }
                    } else {
                        isFilterNameSet = false
                    }
                } else {
                    navController.navigate(Routes.FilterSetListScreen.route) {
                        popUpTo("FilterSetListScreen") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        }
    ) { PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = filterSetName,
                onValueChange = { filterSetName = it },
                label = { Text("필터 이름") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )
            if (!isFilterNameSet) {
                Text(
                    "필터 이름은 필수항목입니다.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (filterSetRepeatFilter.any { it }) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            mainViewModel.filterSetName = filterSetName
                            mainViewModel.filterSetRepeatFilter = filterSetRepeatFilter
                            mainViewModel.filterSetGroupFilter = filterSetGroupFilter
                            navController.navigate(Routes.RepeatFilterLabel.route) {
                                launchSingleTop = true
                            }
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
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .width(300.dp)
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
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            mainViewModel.filterSetName = filterSetName
                            mainViewModel.filterSetRepeatFilter = filterSetRepeatFilter
                            mainViewModel.filterSetGroupFilter = filterSetGroupFilter
                            navController.navigate(Routes.GroupFilterLabel.route) {
                                launchSingleTop = true
                            }
                        },
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
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .width(300.dp)
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
                onClick = {
                    mainViewModel.filterSetName = filterSetName
                    navController.navigate(Routes.RepeatFilterLabel.route) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "반복 필터 추가")
            }
            Button(
                onClick = {
                    mainViewModel.filterSetName = filterSetName
                    navController.navigate(Routes.GroupFilterLabel.route) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "그룹 필터 추가")
            }
        }
    }
}