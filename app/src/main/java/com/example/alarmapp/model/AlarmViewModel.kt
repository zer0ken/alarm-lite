package com.example.alarmapp.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * AlarmListView에서 사용하는 상태 정보 등을 저장하는 ViewModel입니다.
 *
 * @property _isSelectMode AlarmListView가 선택 모드인지를 나타내는 상태
 * @property _alarmStates 알람의 id에 AlarmState 객체를 대응시키는 상태 맵
 * @property _alarmGroupStates 알람 그룹의 groupName에 AlarmGroupState 객체를 대응시키는 상태 맵
 *
 * @author 이현령
 */
class AlarmViewModel : ViewModel() {
    private var _isSelectMode = mutableStateOf(false)

    private val _alarmStates = mutableStateMapOf<Int, AlarmState>()
    private val _alarmGroupStates = mutableStateMapOf<String, AlarmGroupState>()

    var isSelectMode
        get() = _isSelectMode.value
        set(value) {
            _isSelectMode.value = value
        }

    fun getAlarmState(id: Int) = _alarmStates[id] ?: AlarmState().also { _alarmStates[id] = it }
    fun getAlarmGroupState(groupName: String) =
        _alarmGroupStates[groupName] ?: AlarmGroupState().also { _alarmGroupStates[groupName] = it }
}

/**
 * AlarmItemView에서 사용하는 상태 정보 등을 저장하는 클래스입니다.
 *
 * @property _isSelected AlarmItemView가 선택된 상태인지를 나타내는 상태
 * @author 이현령
 */
class AlarmState {
    private val _isSelected: MutableState<Boolean> = mutableStateOf(false)

    var isSelected: Boolean
        get() = _isSelected.value
        set(value) {
            _isSelected.value = value
        }
}

/**
 * groupedAlarmItems에서 사용하는 상태 정보 등을 저장하는 클래스입니다.
 *
 * @property _isFolded groupedAlarmItems가 펼쳐진 상태인지를 나타내는 상태
 * @author 이현령
 */
class AlarmGroupState{
    private val _isFolded: MutableState<Boolean> = mutableStateOf(true)

    var isFolded: Boolean
        get() = _isFolded.value
        set(value) {
            _isFolded.value = value
        }
}