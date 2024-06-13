package com.example.alarmapp.view.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alarmapp.R
import com.example.alarmapp.Routes
import com.example.alarmapp.model.AlarmGroupState
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.ui.theme.background
import com.example.alarmapp.view.IconToggleButton_

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.groupedAlarmItems(
    alarms: List<AlarmState>,
    alarmGroup: AlarmGroupState,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    alarmGroupStickyHeader(alarmGroup = alarmGroup)
    if (alarmGroup.isFolded) {
        foldedAlarmGroupItems(alarms, alarmGroup, mainViewModel, navController)
    } else {
        expandedAlarmGroupItems(alarms, alarmGroup, mainViewModel, navController)
    }
    stickyHeader {}
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.alarmGroupStickyHeader(
    alarmGroup: AlarmGroupState
) {
    stickyHeader(key = alarmGroup.groupName) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to background,
                        0.7f to background,
                        1.0f to Color.Transparent,
                        startY = 0.8f,
                    )
                )
                .padding(start = 20.dp, end = 14.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            alarmGroup.isFolded = !alarmGroup.isFolded
                        }
                    )
                }
                .animateItemPlacement(),
        ) {
            FoldButton(isFolded = alarmGroup.isFolded, onFoldedChange = { alarmGroup.isFolded = it })
            Text(
                text = alarmGroup.groupName,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            IconToggleButton_(imageVector = Icons.Outlined.Add)
            IconToggleButton_(imageVector = Icons.Outlined.MoreVert)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.foldedAlarmGroupItems(
    alarms: List<AlarmState>,
    groupState: AlarmGroupState,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    item(key = groupState.groupName.hashCode()) {
        LazyRow(
            contentPadding = PaddingValues(start = 32.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .animateItemPlacement()
        ) {
            items(alarms, key = { it.id }) { alarm ->
                AlarmItemView(
                    alarm = alarm,
                    mainViewModel = mainViewModel,
                    navController = navController,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
        Spacer(
            modifier = Modifier
                .width(12.dp)
                .height(12.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.expandedAlarmGroupItems(
    alarms: List<AlarmState>,
    alarmGroup: AlarmGroupState,
    alarmViewModel: MainViewModel,
    navController :NavController
) {
    items(alarms, key = { it.id }) { alarm ->
        Box(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .animateItemPlacement()
        ) {
            AlarmItemView(
                alarm = alarm,
                alarmGroup = alarmGroup,
                mainViewModel = alarmViewModel,
                navController = navController,
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
    item {
        Spacer(
            modifier = Modifier
                .width(14.dp)
                .height(14.dp)
        )
    }
}

@Composable
fun FoldButton(isFolded: Boolean, onFoldedChange: (Boolean) -> Unit) {
    val isExpanded = !isFolded
    val painter = if (isExpanded) {
        painterResource(id = R.drawable.baseline_unfold_less_24)
    } else {
        painterResource(id = R.drawable.baseline_unfold_more_24)
    }
    IconToggleButton_(
        painter = painter,
        checked = isExpanded,
        onCheckedChange = { onFoldedChange(!it) }
    )
}