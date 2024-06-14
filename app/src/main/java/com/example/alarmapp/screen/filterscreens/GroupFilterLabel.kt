package com.example.alarmapp.screen.filterscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarmapp.model.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupFilterLabel(mainViewModel: MainViewModel) {

    val AlarmGroupList by mainViewModel.AlarmGroupList.collectAsState()
    val checkedStates = remember { mutableStateListOf<Boolean>().apply { addAll(List(AlarmGroupList.size) { false }) } }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "그룹 필터",
//                            fontSize = 32.sp,
                            fontWeight = FontWeight(800)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { /* 데이터베이스에 필터 셋 추가 */ }) {
                        Text(text = "저장")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            itemsIndexed(AlarmGroupList) { index, label ->
                val shape: Shape = when (index) {
                    0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    AlarmGroupList.size - 1 -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    else -> RoundedCornerShape(0.dp)
                }

                Card(
                    shape = shape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = if (index == 0) 16.dp else 0.dp, bottom = if (index == AlarmGroupList.size - 1) 16.dp else 0.dp)
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
                                text = label.groupName,
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
                        if (index < AlarmGroupList.size - 1) {
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