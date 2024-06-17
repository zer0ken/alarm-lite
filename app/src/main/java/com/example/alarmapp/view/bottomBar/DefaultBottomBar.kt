package com.example.alarmapp.view.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.model.MainViewModel

@Composable
fun DefaultBottomBar(navController: NavController, mainViewModel: MainViewModel) {
    var isFilterSetMenuExpanded by remember { mutableStateOf(false) }

    val definedRepeatFilters = mainViewModel.repeatFilterDropdown
    val alarmGroups = remember {
        mainViewModel.alarmGroupStateMap.values.toList()
    }
    val filterMap = remember {
        mainViewModel.filterMap.values.toList()
    }

    val selectedFilterSet = mainViewModel.selectedFilterSet
    val selectedRepeatFiltersIndex = mainViewModel.selectedRepeatFiltersIndex
    val selectedGroupFilters = mainViewModel.selectedGroupFilters

    val combinedFilters = mainViewModel.combinedFilters

    val dropdownText = if (combinedFilters.isEmpty()) {
        "필터"
    } else {
        if (combinedFilters.size > 1)
            "${combinedFilters[0].let { 
                if (it.length > 7) {
                    it.take(7) + "..."
                } else {
                    it
                }
            }} 등 ${combinedFilters.size}개의 필터"
        else
            combinedFilters[0]
    }

    val icon = if (combinedFilters.isEmpty()) {
        R.drawable.outline_filter_alt_24
    } else {
        R.drawable.baseline_filter_alt_24
    }

    BottomAppBar(
        modifier = Modifier
            .height(60.dp)
            .clickable { isFilterSetMenuExpanded = !isFilterSetMenuExpanded }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Filter icon"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = dropdownText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(225.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                DropdownMenu(
                    expanded = isFilterSetMenuExpanded,
                    onDismissRequest = { isFilterSetMenuExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(300.dp)
                ) {
                    var count = 0
                    filterMap.map { it.name }.forEach { filter ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "필터: $filter",
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (filter in selectedFilterSet) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                if (filter in selectedFilterSet) {
                                    selectedFilterSet.remove(filter)
                                } else {
                                    selectedFilterSet.add(filter)
                                }
                                if (filter in combinedFilters) {
                                    mainViewModel.removeCombinedFilter(filter)
                                } else {
                                    mainViewModel.addCombinedFilter(filter)
                                }
                            }
                        )
                        count++
                    }
                    if (count > 0) {
                        HorizontalDivider()
                        count = 0
                    }
                    alarmGroups.map { it.groupName }.forEach { filter ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "그룹: $filter",
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (filter in selectedGroupFilters) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                if (filter in selectedGroupFilters) {
                                    selectedGroupFilters.remove(filter)
                                } else {
                                    selectedGroupFilters.add(filter)
                                }
                                if (filter in combinedFilters) {
                                    mainViewModel.removeCombinedFilter(filter)
                                } else {
                                    mainViewModel.addCombinedFilter(filter)
                                }
                            }
                        )
                        count++
                    }
                    if (count > 0) {
                        HorizontalDivider()
                        count = 0
                    }
                    definedRepeatFilters.map { it }.forEach { filter ->
                        val filterIndex = dayOfWeekStringToIndex(filter)
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "반복: $filter",
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (filterIndex in selectedRepeatFiltersIndex) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {
                                if (filterIndex in selectedRepeatFiltersIndex) {
                                    selectedRepeatFiltersIndex.remove(filterIndex)
                                } else {
                                    selectedRepeatFiltersIndex.add(filterIndex)
                                }
                                if (filter in combinedFilters) {
                                    mainViewModel.removeCombinedFilter(filter)
                                } else {
                                    mainViewModel.addCombinedFilter(filter)
                                }
                            }
                        )
                        count++
                    }
                    if (count > 0) {
                        HorizontalDivider()
                        count = 0
                    }
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "필터 관리",
                                    color = Color.Red
                                )
                            }
                        },
                        onClick = { navController.navigate(Routes.FilterSetListScreen.route) }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AnimatedVisibility(
                visible = combinedFilters.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = {
                    mainViewModel.resetSelect()
                    mainViewModel.clearCombinedFilters()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "remove"
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

fun dayOfWeekStringToIndex(dayOfWeek: String): Int {
    return when (dayOfWeek) {
        "일요일에 울리는 알람" -> 0
        "월요일에 울리는 알람" -> 1
        "화요일에 울리는 알람" -> 2
        "수요일에 울리는 알람" -> 3
        "목요일에 울리는 알람" -> 4
        "금요일에 울리는 알람" -> 5
        "토요일에 울리는 알람" -> 6
        else -> throw IllegalArgumentException("Invalid day Of Week: $dayOfWeek")
    }
}