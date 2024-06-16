package com.example.alarmapp.view.bottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.alarmapp.model.MainViewModel

@Composable
fun EditBottomBar(mainViewModel: MainViewModel) {
    var selected by remember { mutableStateOf(false) }
    val selectedText = if (!selected) "전체 선택" else "전체 해제"

    var onOff by remember { mutableStateOf(false) }
    val onOffText = if (!onOff) "끄기" else "켜기"

    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    selected = !selected
                    mainViewModel.toggleSelectAll(selected)
                }
            ) {
                Text(text = selectedText)
            }
            TextButton(
                onClick = {
                    onOff = !onOff
                    mainViewModel.OnOffSelectedAlarms(onOff)
                }) {
                Text(text = onOffText)
            }
//            TextButton(onClick = { isGroupMenuExpanded = true }) {
//                Text(text = "그룹화")
//            }
            TextButton(onClick = { mainViewModel.clearGroupForSelectedAlarms() }) {
                Text(text = "그룹 해제")
            }
            TextButton(onClick = { mainViewModel.deleteSelectedAlarms() }) {
                Text(text = "삭제")
            }
        }
    }
}