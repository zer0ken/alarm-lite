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
import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.alarmdata.AlarmViewModel
import com.example.alarmapp.ui.theme.background
import com.example.alarmapp.view.IconToggleButton_

/**
 * LazyColumn 내에서 알람 그룹과 그에 속한 알람을 표시하는 LazyListScope 함수입니다.
 *
 * @param alarms 표시하고자 하는 알람이 담긴 List
 * @param alarmGroup 표시하고자 하는 알람 그룹의 정보
 * @param alarmViewModel AlarmViewModel의 인스턴스
 * @author 이현령
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.groupedAlarmItems(
    alarms: List<Alarm>,
    alarmGroup: AlarmGroup,
    alarmViewModel: AlarmViewModel,
    navController: NavController
) {
    val alarmGroupState = alarmViewModel.getAlarmGroupState(alarmGroup.groupName)

    alarmGroupStickyHeader(
        alarmGroup = alarmGroup,
        alarmViewModel = alarmViewModel
    )
    if (alarmGroupState.isFolded) {
        foldedAlarmGroupItems(alarms, alarmViewModel, navController)
    } else {
        expandedAlarmGroupItems(alarms, alarmViewModel, navController)
    }
    stickyHeader {}
}

/**
 * LazyColumn 내에서 알람 그룹의 stickyHeader 내에 들어갈 요소를 표시하는 컴포저블 함수입니다.
 *
 * @param alarmGroup 표시하고자 하는 알람 그룹의 정보
 * @param alarmViewModel AlarmViewModel의 인스턴스
 * @author 이현령
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.alarmGroupStickyHeader(
    alarmGroup: AlarmGroup,
    alarmViewModel: AlarmViewModel
) {
    val state = alarmViewModel.getAlarmGroupState(alarmGroup.groupName)

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
                            state.isFolded = !state.isFolded
                        }
                    )
                }
                .animateItemPlacement(),
        ) {
            FoldButton(isFolded = state.isFolded, onFoldedChange = { state.isFolded = it })
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

/**
 * LazyColumn 내에서 접힌 그룹을 표시하는 LazyListScope 함수입니다.
 *
 * @param alarms 표시하고자 하는 알람이 담긴 List
 * @param alarmViewModel AlarmViewModel의 인스턴스
 * @author 이현령
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.foldedAlarmGroupItems(
    alarms: List<Alarm>,
    alarmViewModel: AlarmViewModel,
    navController: NavController
) {
    item(key = alarms.hashCode()) {
        LazyRow(
            contentPadding = PaddingValues(start = 32.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .animateItemPlacement()
        ) {
            items(alarms, key = { it.id }) { alarm ->
                AlarmItemView(
                    alarm = alarm,
                    alarmViewModel = alarmViewModel,
                    modifier = Modifier.animateItemPlacement()
                ){
                    alarmViewModel.editAlarm(alarm)
                    alarmViewModel.removeAlarm(alarm.id)
                    navController.navigate(Routes.AddUnitAlarm.route)
                }
            }
        }
        Spacer(
            modifier = Modifier
                .width(12.dp)
                .height(12.dp)
        )
    }
}

/**
 * LazyColumn 내에서 펼져진 그룹을 표시하는 LazyListScope 함수입니다.
 *
 * @param alarms 표시하고자 하는 알람이 담긴 List
 * @param alarmViewModel AlarmViewModel의 인스턴스
 * @author 이현령
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.expandedAlarmGroupItems(
    alarms: List<Alarm>,
    alarmViewModel: AlarmViewModel,
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
                alarmViewModel = alarmViewModel,
                modifier = Modifier.animateItemPlacement()
            ){
                alarmViewModel.editAlarm(alarm)
                alarmViewModel.removeAlarm(alarm.id)
                navController.navigate(Routes.AddUnitAlarm.route)
            }
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

/**
 * 알람 그룹의 접힘 여부를 바꾸는 버튼을 표시하는 컴포저블 함수입니다.
 *
 * @param isFolded 알람 그룹의 접힘 여부
 * @param onFoldedChange 접힘 여부를 바꾸는 콜백 함수
 * @author 이현령
 */
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