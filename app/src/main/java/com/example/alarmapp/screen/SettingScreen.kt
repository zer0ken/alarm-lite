package com.example.alarmapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.model.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController, mainViewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    var is24HourView by remember { mutableStateOf(mainViewModel.is24HourView) }

    LaunchedEffect(mainViewModel.is24HourView) {
        is24HourView = mainViewModel.is24HourView
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("설정") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("24시간제 표기", modifier = Modifier.weight(1f))
                Switch(
                    checked = is24HourView,
                    onCheckedChange = {
                        is24HourView = it
                        scope.launch {
                            mainViewModel.is24HourView = it
                        }
                    }
                )
            }
            Button(
                onClick = {
                    scope.launch {
                        mainViewModel.cleanupAlarms()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("알람 정리")
            }
        }
    }
}
