package com.example.alarmapp.screen.filterscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.view.FilterTopAppBar
import com.example.alarmapp.view.bottomBar.dayOfWeekToString

@Composable
fun RepeatFilterLabel(navController: NavController, mainViewModel: MainViewModel) {

    val definedRepeatFilters = remember { mainViewModel.definedRepeatFilters }
//    val selectedRepeatFilters = mainViewModel.selectedRepeatFilters
//
//    val checkedStates = remember { mutableStateListOf<Boolean>().apply {
//        addAll(definedRepeatFilters.map { it.toString() in selectedRepeatFilters })
//    }}
    val checkedStates = remember { mutableStateListOf<Boolean>().apply { addAll(List(definedRepeatFilters.size) { false }) } }

    Scaffold(
        topBar = {
            FilterTopAppBar("반복 필터") {
                if (it) {
                    val selectedDays =
                        definedRepeatFilters.filterIndexed { index, _ -> checkedStates[index] }
//                    val repeatFilter = RepeatFilter(week = selectedDays)
//                    mainViewModel.filterSetRepeatFilter.value = repeatFilter
                }
                navController.navigate(Routes.AddFilterSetScreen.route)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            itemsIndexed(definedRepeatFilters) { index, label ->
                val shape: Shape = when (index) {
                    0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    definedRepeatFilters.size - 1 -> RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                    else -> RoundedCornerShape(0.dp)
                }

                Card(
                    shape = shape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(
                            top = if (index == 0) 16.dp else 0.dp,
                            bottom = if (index == definedRepeatFilters.size - 1) 16.dp else 0.dp
                        )
                        .clickable {
                            checkedStates[index] = !checkedStates[index]
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = label,
                                modifier = Modifier.weight(1f)
                            )
                            if (checkedStates[index]) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Checked",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        if (index < definedRepeatFilters.size - 1) {
                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}