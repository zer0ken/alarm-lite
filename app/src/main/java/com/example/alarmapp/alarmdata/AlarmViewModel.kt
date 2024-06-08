package com.example.alarmapp.alarmdata

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmViewModel : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>()
    val alarms: LiveData<List<Alarm>> get() = _alarms

    // 알람의 필드
    val hour = MutableLiveData<Int>()
    val minute = MutableLiveData<Int>()
    val groupName = MutableLiveData<String>()
    val bookmark = MutableLiveData<Boolean>()
    val weekTerm = MutableLiveData<Int>()
    val isOn = MutableLiveData<Boolean>()

    init {
        _alarms.value = AlarmManager.alarmList
    }

    fun setHour(newHour: Int) {
        hour.value = newHour
    }

    fun setMinute(newMinute: Int) {
        minute.value = newMinute
    }

    fun setGroupName(newGroupName: String) {
        groupName.value = newGroupName
    }

    fun setBookmark(newBookmark: Boolean) {
        bookmark.value = newBookmark
    }

    fun setWeekTerm(newWeekTerm: Int) {
        weekTerm.value = newWeekTerm
    }

    fun setIsOn(newIsOn: Boolean) {
        isOn.value = newIsOn
    }

    // 새로운 알람을 생성하고 추가
    fun makeAlarm() {
        val alarm = Alarm(
            hour = hour.value ?: 0,
            minute = minute.value ?: 0,
            groupName = groupName.value ?: "",
            bookmark = bookmark.value ?: false,
            weekTerm = weekTerm.value ?: 1,
            isOn = isOn.value ?: true,
            updatedTime = System.currentTimeMillis(),
            repeatDays = SnapshotStateList()
        )
        AlarmManager.addAlarm(alarm)
        _alarms.value = AlarmManager.alarmList
    }

    // 기존의 알람 객체를 생성했다면, 이를 추가
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
