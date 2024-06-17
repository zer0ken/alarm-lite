package com.example.alarmapp.view.bottomBar

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

    Log.d("test11", selectedFilterSet.toString())
    Log.d("test11", selectedRepeatFiltersIndex.toString())
    Log.d("test11", selectedGroupFilters.toString())

    val combinedFilters = remember {
        mutableListOf<String>().apply {
            addAll(selectedFilterSet)
            addAll(selectedRepeatFiltersIndex.map { indexToDayOfWeekString(it)})
            addAll(selectedGroupFilters)
        }
    }
    Log.d("test", combinedFilters.toString())

    val dropdownText = if (combinedFilters.isEmpty()) {
        "필터"
    } else {
        combinedFilters.joinToString(separator = ", ")
    }

    val icon = if (combinedFilters.isEmpty()) {
        R.drawable.outline_filter_alt_24
    } else {
        R.drawable.baseline_filter_alt_24
    }

    BottomAppBar(
        modifier = Modifier.height(60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier
                .clickable { isFilterSetMenuExpanded = !isFilterSetMenuExpanded }
                .padding(10.dp)
            ) {
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
                    modifier = Modifier.width(90.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                DropdownMenu(
                    expanded = isFilterSetMenuExpanded,
                    onDismissRequest = { isFilterSetMenuExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(300.dp)
                ) {
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
//                                if (filter in selectedFilterSet) {
//                                    selectedFilterSet.remove(filter)
//                                } else {
//                                    selectedFilterSet.add(filter)
//                                }
                                mainViewModel.selected3(filter)
                            }
                        )
                    }
                    Divider()
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
//                                if (filterIndex in selectedRepeatFiltersIndex) {
//                                    selectedRepeatFiltersIndex.remove(filterIndex)
//                                } else {
//                                    selectedRepeatFiltersIndex.add(filterIndex)
//                                }
                                mainViewModel.selected2(filter)
                            }
                        )
                    }
                    Divider()
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
//                                if (filter in selectedGroupFilters) {
//                                    selectedGroupFilters.remove(filter)
//                                } else {
//                                    selectedGroupFilters.add(filter)
//                                }
                                mainViewModel.selected1(filter)
                            }
                        )
                    }
                    Divider()
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
            IconButton(onClick = {
                mainViewModel.resetSelect()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "remove"
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
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

fun indexToDayOfWeekString(index: Int): String {
    return when (index) {
        0 -> "일요일에 울리는 알람"
        1 -> "월요일에 울리는 알람"
        2 -> "화요일에 울리는 알람"
        3 -> "수요일에 울리는 알람"
        4 -> "목요일에 울리는 알람"
        5 -> "금요일에 울리는 알람"
        6 -> "토요일에 울리는 알람"
        else -> throw IllegalArgumentException("Invalid index: $index")
    }
}