package com.example.alarmapp.database

import com.example.alarmapp.database.alarm.AlarmEntity
import com.example.alarmapp.database.alarmgroup.AlarmGroupEntity
import com.example.alarmapp.database.filter.FilterEntity
import com.example.alarmapp.model.AlarmGroupState
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.Filter

class Repository(private val db: AlarmDatabase) {
    private val alarmDao = db.alarmDao()
    private val alarmGroupDao = db.alarmGroupDao()
    private val filterDao = db.filterDao()

    suspend fun getAlarms(): List<AlarmState> = alarmDao.getAll()
        .map { alarm -> fromAlarmEntity(alarm) }

    suspend fun getAlarmGroups(): List<AlarmGroupState> = alarmGroupDao.getAll()
        .map { alarmGroup -> fromAlarmGroupEntity(alarmGroup) }

    suspend fun getFilters(): List<Filter> = filterDao.getAll()
        .map { filter -> fromFilterEntity(filter) }

    suspend fun getAlarm(id: Int): AlarmState? {
        val found = alarmDao.get(id)
        if (found.isEmpty()) {
            return null
        }
        return fromAlarmEntity(found[0])
    }

    suspend fun insert(alarm: AlarmState) {
        alarmDao.insert(toAlarmEntity(alarm))
    }

    suspend fun insert(alarmGroup: AlarmGroupState) {
        alarmGroupDao.insert(toAlarmGroupEntity(alarmGroup))
    }

    suspend fun insert(filter: Filter) {
        filterDao.insert(toFilterEntity(filter))
    }

    suspend fun update(alarm: AlarmState) {
        alarmDao.update(toAlarmEntity(alarm))
    }

    suspend fun update(alarmGroup: AlarmGroupState) {
        alarmGroupDao.update(toAlarmGroupEntity(alarmGroup))
    }

    suspend fun update(filter: Filter) {
        filterDao.update(toFilterEntity(filter))
    }

    suspend fun delete(alarm: AlarmState) {
        alarmDao.delete(toAlarmEntity(alarm))
    }

    suspend fun delete(alarmGroup: AlarmGroupState) {
        alarmGroupDao.delete(toAlarmGroupEntity(alarmGroup))
    }

    suspend fun delete(filter: Filter) {
        filterDao.delete(toFilterEntity(filter))
    }

    private fun fromAlarmEntity(value: AlarmEntity) = AlarmState(
        id = value.id,
        hour = value.hour,
        minute = value.minute,
        repeatOnWeekdays = value.repeatOnWeekdays,
        name = value.name,
        groupName = value.groupName ?: "",
        isOn = value.isOn,
        isBookmarked = value.isBookmarked,
        isRingtoneOn = value.isRingtoneOn,
        selectedRingtoneUri = value.selectedRingtoneUri
    )

    private fun toAlarmEntity(value: AlarmState) = AlarmEntity(
        id = value.id,
        hour = value.hour,
        minute = value.minute,
        repeatOnWeekdays = value.repeatOnWeekdays,
        name = value.name,
        groupName = value.groupName.ifBlank { null },
        isOn = value.isOn,
        isBookmarked = value.isBookmarked,
        isRingtoneOn = value.isRingtoneOn,
        selectedRingtoneUri = value.selectedRingtoneUri
    )

    private fun fromAlarmGroupEntity(value: AlarmGroupEntity) = AlarmGroupState(
        value.name
    )

    private fun toAlarmGroupEntity(value: AlarmGroupState) = AlarmGroupEntity(
        value.groupName
    )

    private fun fromFilterEntity(value: FilterEntity) = Filter(
        value.name,
        value.repeatFilter,
        value.groupFilter
    )

    private fun toFilterEntity(value: Filter) = FilterEntity(
        value.title,
        value.repeatFilter,
        value.groupFilter
    )
}