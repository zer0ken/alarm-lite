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

@Composable
fun MultiSelectBottomBar() {
    val buttonOnOff by remember { mutableStateOf(false) }
    val textOnOff = if (!buttonOnOff) "끄기" else "켜기"

    var isGroupMenuExpanded by remember { mutableStateOf(false) }

    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    /* 선택된 알람 객체의 isOn = buttonOnOff */
                    !buttonOnOff
                }
            ) {
                Text(text = textOnOff)
            }
            TextButton(onClick = { /*  */ }) {
                Text(text = "뮤트")
            }
            TextButton(onClick = { isGroupMenuExpanded = true }) {
                Text(text = "그룹 관리")
            }
            DropdownMenu(
                expanded = isGroupMenuExpanded,
                onDismissRequest = { isGroupMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "그룹 생성"
                            )
                        }
                    },
                    onClick = { /* 그룹 생성 화면으로 이동 */ }
                )
                Divider()
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("그룹 해제")
                        }
                    },
                    onClick = { /* 선택된 알람 객체의 groupName = "" */ }
                )
                Divider()
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("그룹 이동")
                        }
                    },
                    onClick = { /* 존재하는 그룹들을 어떻게 보여줄거냐 */ }
                )
            }
            TextButton(onClick = { /* ??? */ }) {
                Text(text = "편집")
            }
            TextButton(onClick = { /* 알람 삭제 */ }) {
                Text(text = "삭제")
            }
        }
    }
}