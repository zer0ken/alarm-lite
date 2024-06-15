package com.example.alarmapp.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.model.rememberAlarmState
import com.example.alarmapp.ui.theme.SaturdayBlue
import com.example.alarmapp.ui.theme.SundayRed
import com.example.alarmapp.view.AlarmSoundPicker
import com.example.alarmapp.view.CancelSaveBottomBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UpdateAlarmScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    alarmState: AlarmState = rememberAlarmState(),
    is24HourView: Boolean = false
) {
    val timePickerState: TimePickerState = rememberTimePickerState()
    timePickerState.hour = alarmState.hour
    timePickerState.minute = alarmState.minute

    val focusManager = LocalFocusManager.current
    val (first, second, third, fourth) = remember { FocusRequester.createRefs() }

    var collapse by remember {
        mutableStateOf(false)
    }

    var groupNameDropdownExposed by remember {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    val weekdays = listOf("일", "월", "화", "수", "목", "금", "토")

    val existingGroups = mainViewModel.alarmGroupStateMap.values

    val current = LocalContext.current

    LaunchedEffect(scrollState.value) {
        if (scrollState.lastScrolledForward) {
            collapse = true
        }
    }

    LaunchedEffect(Unit) {
        third.captureFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "알람 추가",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            collapse = !collapse
                            focusManager.clearFocus()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_access_time_24),
                            contentDescription = "시간 선택기 펼치기"
                        )
                    }
                }
            )
        },
        bottomBar = {
            CancelSaveBottomBar { isSave->
                if (isSave) {
                    val existingAlarm = mainViewModel.alarmStateMap.values.find {
                        it.hour == timePickerState.hour && it.minute == timePickerState.minute
                    }
                    if (existingAlarm != null){
                        Toast.makeText(current, "알람 목록에 동일한 알람이 있거나 시간이 수정되지 않았습니다.", Toast.LENGTH_LONG).show()
                        navController.popBackStack(Routes.MainScreen.route,false)
                    }
                    else{
                        alarmState.hour = timePickerState.hour
                        alarmState.minute = timePickerState.minute
                        mainViewModel.updateAlarm(alarmState)
                        navController.popBackStack(Routes.MainScreen.route,false)
                    }
                }
                else {
                    navController.popBackStack(Routes.MainScreen.route,false)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AnimatedVisibility(
                        visible = !collapse,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        TimePicker(
                            state = timePickerState,
                            modifier = Modifier
                                .focusRequester(first)
                                .focusProperties {
                                    next = third
                                }
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AnimatedVisibility(
                        visible = collapse,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        TimeInput(
                            state = timePickerState,
                            modifier = Modifier
                                .focusRequester(second)
                                .focusProperties {
                                    next = third
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(vertical = 22.dp)
                        .onFocusChanged {
                            if (it.hasFocus) {
                                collapse = true
                            }
                        }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp)
                    ) {
                        repeat(7) { idx ->
                            val modifier = if (alarmState.repeatOnWeekdays[idx]) {
                                Modifier.border(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                )
                            } else {
                                Modifier
                            }
                            TextButton(
                                onClick = {
                                    alarmState.repeatOnWeekdays[idx] =
                                        !alarmState.repeatOnWeekdays[idx]
                                },
                                modifier = modifier
                                    .size(42.dp)
                            ) {
                                Text(
                                    text = weekdays[idx],
                                    color = when (idx) {
                                        0 -> SundayRed
                                        6 -> SaturdayBlue
                                        else -> MaterialTheme.colorScheme.primary
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = alarmState.name,
                        onValueChange = { alarmState.name = it },
                        label = { Text(text = "알람 이름") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                        ),
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Clear,
                                contentDescription = "이름 제거",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .padding(all = 2.dp)
                                    .combinedClickable(
                                        onClick = {
                                            alarmState.name = ""
                                        }
                                    )
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 22.dp)
                            .focusRequester(third)
                            .focusProperties {
                                previous = first
                                next = fourth
                            }
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ExposedDropdownMenuBox(
                        expanded = groupNameDropdownExposed,
                        onExpandedChange = {
                            groupNameDropdownExposed = !existingGroups.isEmpty() && it
                        },
                        modifier = Modifier.padding(horizontal = 22.dp)
                    ) {
                        TextField(
                            value = alarmState.groupName,
                            onValueChange = { alarmState.groupName = it },
                            label = { Text(text = "그룹 이름") },
                            trailingIcon = {
                                Icon(
                                    Icons.Filled.Clear,
                                    contentDescription = "그룹에서 제외",
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .padding(all = 2.dp)
                                        .combinedClickable(
                                            onClick = {
                                                alarmState.groupName = ""
                                            }
                                        )
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                            ),
                            modifier = Modifier
                                .focusRequester(fourth)
                                .focusProperties {
                                    previous = third
                                }
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = groupNameDropdownExposed,
                            onDismissRequest = { groupNameDropdownExposed = false }) {
                            existingGroups.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.groupName) },
                                    onClick = { alarmState.groupName = it.groupName },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { alarmState.isBookmarked = !alarmState.isBookmarked }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp, vertical = 12.dp)
                        ) {
                            Text(text = "즐겨찾기")
                            Switch(
                                checked = alarmState.isBookmarked,
                                onCheckedChange = { alarmState.isBookmarked = it },
                                modifier = Modifier
                                    .size(10.dp)
                                    .scale(0.6f)
                                    .padding(end = 12.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    AlarmSoundPicker(
                        LocalContext.current,
                        alarmState,
                        Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
