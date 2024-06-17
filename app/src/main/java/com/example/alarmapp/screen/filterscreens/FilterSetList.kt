package com.example.alarmapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.Routes
import com.example.alarmapp.model.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSetListScreen(navController: NavController, mainViewModel: MainViewModel) {

    val filterMap = remember {
        mainViewModel.filterMap.values.toList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "필터 셋 목록",
                            fontWeight = FontWeight(800)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.MainScreen.route) }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                items(filterMap) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .height(66.dp)
                            .clickable {
                                navController.navigate("UpdateFilterSetScreen/${it.name}")
                            }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = it.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(700)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { mainViewModel.deleteFilter(it) }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "delete",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {
                    mainViewModel.resetFilter()
                    navController.navigate(Routes.AddFilterSetScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "필터 셋 추가")
            }
        }
    }
}