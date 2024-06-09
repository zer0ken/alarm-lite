package com.example.alarmapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFilterSetScreen() {

    val scrollState = rememberScrollState()
    var filterSetName by remember { mutableStateOf("")}
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
//                    .padding(vertical = 10.dp),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "필터 셋 작성",
                            fontSize = 32.sp,
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
    ) {PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues)
                .verticalScroll(scrollState)
        ) {
            OutlinedTextField(
                value = filterSetName,
                onValueChange = { filterSetName = it },
                label = { Text("필터 이름")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp))

            /* 추가한 필터 불러오기 */

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
                    onClick = { /* LabelScreen 으로 이동 */ }
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
                    onClick = { /* LabelScreen 으로 이동 */ }
                )
            }
        }
    }
}