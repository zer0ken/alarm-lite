package com.example.alarmapp.view.bottomBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.model.MainViewModel
import java.time.DayOfWeek

@Composable
fun DefaultBottomBar(navController: NavController, mainViewModel: MainViewModel){
    var isFilterSetMenuExpanded by remember { mutableStateOf(false) }

    val definedRepeatFilters = mainViewModel.definedRepeatFilters // 월요일마다, ..
    val alarmGroups = remember {
        mainViewModel.alarmGroupStateMap.values.toList()
    }
    val filterMap = remember {
        mainViewModel.filterMap.values.toList()
    }

    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "필터",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                contentDescription = "Dropdown",
                modifier = Modifier.clickable { isFilterSetMenuExpanded = true }
            )
            DropdownMenu(
                expanded = isFilterSetMenuExpanded,
                onDismissRequest = { isFilterSetMenuExpanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(300.dp)
            ) {
                definedRepeatFilters.map { it }.forEach { filter ->
                    val filterIndex = dayOfWeekStringToIndex(filter)
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = filter,
                                    modifier = Modifier.weight(1f)
                                )
                                if (filterIndex in mainViewModel.selectedRepeatFiltersIndex) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        },
                        onClick = {
                            if (filterIndex in mainViewModel.selectedRepeatFiltersIndex) {
                                mainViewModel.selectedRepeatFiltersIndex.remove(filterIndex)
                            } else {
                                mainViewModel.selectedRepeatFiltersIndex.add(filterIndex)
                            }
                        }
                    )
                    Divider()
                }
                alarmGroups.map { it.groupName }.forEach { filter ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = filter,
                                    modifier = Modifier.weight(1f)
                                )
                                if (filter in mainViewModel.selectedGroupFilters) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        },
                        onClick = {
                            if (filter in mainViewModel.selectedGroupFilters) {
                                mainViewModel.selectedGroupFilters.remove(filter)
                            } else {
                                mainViewModel.selectedGroupFilters.add(filter)
                            }
                        }
                    )
                    Divider()
                }
                filterMap.map { it.name }.forEach { filter ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = filter,
                                    modifier = Modifier.weight(1f)
                                )
                                if (filter in mainViewModel.selectedFilterSet) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        },
                        onClick = {
                            if (filter in mainViewModel.selectedFilterSet) {
                                mainViewModel.selectedFilterSet.remove(filter)
                            } else {
                                mainViewModel.selectedFilterSet.add(filter)
                            }
                        }
                    )
                    Divider()
                }
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("필터 관리",
                                color = Color.Red)
                        }
                    },
                    onClick = { navController.navigate(Routes.FilterSetListScreen.route) }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.clickable {
                    mainViewModel.resetFilter()
                    navController.navigate(Routes.AddFilterSetScreen.route)
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

fun dayOfWeekStringToIndex(dayOfWeek: String): Int {
    return when (dayOfWeek) {
        "일요일마다" -> 0
        "월요일마다" -> 1
        "화요일마다" -> 2
        "수요일마다" -> 3
        "목요일마다" -> 4
        "금요일마다" -> 5
        "토요일마다" -> 6
        else -> throw IllegalArgumentException("Invalid day Of Week: $dayOfWeek")
    }
}