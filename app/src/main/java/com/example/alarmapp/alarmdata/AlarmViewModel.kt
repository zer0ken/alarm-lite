package com.example.alarmapp.alarmdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmViewModel : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>()
    val alarms: LiveData<List<Alarm>> get() = _alarms

    init {
        _alarms.value = AlarmManager.alarmList
    }

    fun addAlarm(alarm: Alarm) {
        AlarmManager.addAlarm(alarm)
        _alarms.value = AlarmManager.alarmList
    }

    fun removeAlarm(id: Int) {
        AlarmManager.removeAlarm(id)
        _alarms.value = AlarmManager.alarmList
    }

    fun toggleOnOff(alarm: Alarm) {
        AlarmManager.toggleOnOff(alarm)
        _alarms.value = AlarmManager.alarmList
    }

    fun toggleBookmark(alarm: Alarm) {
        AlarmManager.toggleBookmark(alarm)
        _alarms.value = AlarmManager.alarmList
    }

    fun getAlarm(id: Int): Alarm? {
        return AlarmManager.getAlarm(id)
    }

    fun getAlarmFromIndex(index: Int): Alarm {
        return AlarmManager.getAlarmFromIndex(index)
    }

    fun removeAlarmFromIndex(index: Int) {
        AlarmManager.removeAlarmFromIndex(index)
        _alarms.value = AlarmManager.alarmList
    }

    fun removeAllAlarms() {
        AlarmManager.removeAllAlarm()
        _alarms.value = AlarmManager.alarmList
    }

    fun addGroup(groupName: String) {
        val alarmGroup = AlarmGroup(groupName)
        AlarmManager.addGroup(alarmGroup)
        _alarms.value = AlarmManager.alarmList
    }

    fun removeGroup(name: String) {
        AlarmManager.removeGroup(name)
        _alarms.value = AlarmManager.alarmList
    }

    fun turnOnAll(alarmGroup: AlarmGroup) {
        AlarmManager.turnOnAll(alarmGroup)
        _alarms.value = AlarmManager.alarmList
    }

    fun turnOffAll(alarmGroup: AlarmGroup) {
        AlarmManager.turnOffAll(alarmGroup)
        _alarms.value = AlarmManager.alarmList
    }

    fun sortAbsolute() {
        AlarmManager.sortAbsolute()
        _alarms.value = AlarmManager.alarmList
    }

    fun sortRelative() {
        AlarmManager.sortRelative()
        _alarms.value = AlarmManager.alarmList
    }
}
