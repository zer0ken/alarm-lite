package com.example.alarmapp.addalarm.alarmgroupselect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.alarmapp.R
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.alarmdata.AlarmManager

@Composable
fun AlarmGroupSelect(alarmManager:AlarmManager) {
    var groupSelectMenuExpanded by remember { mutableStateOf(false) }
    var selectedGroup by remember { mutableStateOf(AlarmGroup("")) }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text =  stringResource(id = R.string.group))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = stringResource(id = R.string.select_group), color = Color.Gray)
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(id = R.string.select_group),
                modifier = Modifier
                    .clickable {
                        groupSelectMenuExpanded = true
                    }
            )
            DropdownMenu(
                expanded = groupSelectMenuExpanded,
                onDismissRequest = { groupSelectMenuExpanded = false },
                modifier = Modifier
                    .width(128.dp)
            ) {
                alarmManager.alarmGroupSet.forEach { alarmGroup ->
                    DropdownMenuItem(
                        text = {
                            Row (
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = alarmGroup.groupName,
                                    color = if(alarmGroup == selectedGroup) Color(0xFF734D4D) else Color.Black
                                )
                                if (alarmGroup == selectedGroup){
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected group",
                                        tint = Color(0xFF734D4D),
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                            }
                        },
                        onClick = {
                            selectedGroup = alarmGroup
                            groupSelectMenuExpanded = false
                        }
                    )
                }
                HorizontalDivider()
                DropdownMenuItem(
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(id = R.string.add_group))
                        }
                    },
                    onClick = {
                        groupSelectMenuExpanded = false
                        // 그룹 추가하는 화면으로 ㅇㅇ 또 짜야된다 
                        // 알람 그룹 명만 새로 입력해서 되는 것으로 하고
                        // 이친구는 알람 그룹 내의 알람으로 만들기
                    }
                )
            }
        }
    }
}