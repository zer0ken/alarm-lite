package com.example.alarmapplication

import android.util.Log
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
import com.example.alarmapp.model.Filter
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.FilterTopAppBar
import java.time.DayOfWeek

@Composable
fun AddFilterSetScreen(navController: NavController, mainViewModel: MainViewModel) {

    val scrollState = rememberScrollState()
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    var filterSetName by mainViewModel.filterSetName
    var filterSetRepeatFilter by mainViewModel.filterSetRepeatFilter
    var filterSetGroupFilter by mainViewModel.filterSetGroupFilter

    fun clearFilterSet(){
        filterSetName = ""
        filterSetRepeatFilter = null
        filterSetGroupFilter = null
    }

    Log.d("test1", filterSetName)
    Log.d("test2", filterSetRepeatFilter.toString())
    Log.d("test3", filterSetGroupFilter.toString())

    Scaffold(
        topBar = {
            FilterTopAppBar("필터 셋 작성") {
                if (it) {
                    mainViewModel.insertFilter(
                        Filter(
                            title = filterSetName,
                            repeatFilter = filterSetRepeatFilter,
                            groupFilter = filterSetGroupFilter
                        )
                    )
                }
                clearFilterSet()
                navController.navigate(Routes.FilterSetListScreen.route)
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

            /* 추가한 필터 불러오기 */
            if (filterSetRepeatFilter != null) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
//                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = "반복 필터",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = filterSetRepeatFilter!!.week.toString(),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { }) {
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
                modifier = Modifier
                    .width(100.dp),
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false },
            ) {
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "반복 필터",
                                fontSize = 16.sp,
                                fontWeight = FontWeight(500),
                            )
                        }
                    },
                    onClick = {
                        navController.navigate(Routes.RepeatFilterLabel.route)
                    }
                )
                Divider()
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "그룹 필터",
                                fontSize = 16.sp,
                                fontWeight = FontWeight(500),
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

fun stringToDayOfWeek(dayString: String): DayOfWeek {
    return when (dayString) {
        "월요일마다" -> DayOfWeek.MONDAY
        "화요일마다" -> DayOfWeek.TUESDAY
        "수요일마다" -> DayOfWeek.WEDNESDAY
        "목요일마다" -> DayOfWeek.THURSDAY
        "금요일마다" -> DayOfWeek.FRIDAY
        "토요일마다" -> DayOfWeek.SATURDAY
        "일요일마다" -> DayOfWeek.SUNDAY
        else -> throw IllegalArgumentException("Invalid day string: $dayString")
    }
}