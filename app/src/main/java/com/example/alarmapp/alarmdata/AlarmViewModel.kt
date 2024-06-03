package com.example.alarmapp.alarmdata

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AlarmViewModel : ViewModel() {

    // UI에서 관찰 가능한 알람 리스트
    val alarms: SnapshotStateList<Alarm> = mutableStateListOf()

    init {
        // AlarmManager의 알람 리스트를 ViewModel의 알람 리스트에 복사
        alarms.addAll(AlarmManager.alarmList)
    }

    // 알람 추가
    fun addAlarm(alarm: Alarm) {
        viewModelScope.launch {
            AlarmManager.addAlarm(alarm)
            alarms.add(alarm)
        }
    }

    // 알람 삭제
    fun removeAlarm(id: Int) {
        viewModelScope.launch {
            val removedAlarm = AlarmManager.removeAlarm(id)
            removedAlarm?.let {
                alarms.remove(it)
            }
        }
    }

    // 알람 전체 삭제
    fun removeAllAlarms() {
        viewModelScope.launch {
            val removedAlarms = AlarmManager.removeAllAlarm()
            alarms.clear()
        }
    }

    // 알람 ON/OFF 토글
    fun toggleAlarmOnOff(id: Int) {
        viewModelScope.launch {
            val alarm = AlarmManager.getAlarm(id)
            alarm?.let {
                AlarmManager.toggleOnOff(it)
                val index = alarms.indexOf(it)
                if (index != -1) {
                    alarms[index] = it
                }
            }
        }
    }

    // 알람 즐겨찾기 토글
    fun toggleAlarmBookmark(id: Int) {
        viewModelScope.launch {
            val alarm = AlarmManager.getAlarm(id)
            alarm?.let {
                AlarmManager.toggleBookmark(it)
                val index = alarms.indexOf(it)
                if (index != -1) {
                    alarms[index] = it
                }
            }
        }
    }

    // 알람 그룹 추가
    fun addGroup(name: String) {
        viewModelScope.launch {
            val newGroup = AlarmGroup(name)
            AlarmManager.addGroup(newGroup)
        }
    }

    // 알람 그룹 삭제
    fun removeGroup(name: String) {
        viewModelScope.launch {
            AlarmManager.removeGroup(name)
        }
    }

    // 알람 그룹 전체 켜기
    fun turnOnAllAlarmsInGroup(name: String) {
        viewModelScope.launch {
            val group = AlarmManager.getGroup(name)
            group?.let {
                AlarmManager.turnOnAll(it)
                alarms.forEach { alarm ->
                    if (alarm.groupName == name) {
                        alarm.isOn = true
                    }
                }
            }
        }
    }

    // 알람 그룹 전체 끄기
    fun turnOffAllAlarmsInGroup(name: String) {
        viewModelScope.launch {
            val group = AlarmManager.getGroup(name)
            group?.let {
                AlarmManager.turnOffAll(it)
                alarms.forEach { alarm ->
                    if (alarm.groupName == name) {
                        alarm.isOn = false
                    }
                }
            }
        }
    }
}
