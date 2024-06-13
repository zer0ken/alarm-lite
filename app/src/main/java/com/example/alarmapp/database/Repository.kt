package com.example.alarmapp.database

import com.example.alarmapp.database.alarm.AlarmEntity
import com.example.alarmapp.database.alarmgroup.AlarmGroupEntity
import com.example.alarmapp.database.filter.FilterEntity
import com.example.alarmapp.model.AlarmGroupState
import com.example.alarmapp.model.AlarmState
import com.example.alarmapp.model.Filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(private val db: AlarmDatabase) {
    private val alarmDao = db.alarmDao()
    private val alarmGroupDao = db.alarmGroupDao()
    private val filterDao = db.filterDao()

    fun getAlarms(): Flow<List<AlarmState>> = alarmDao.getAll()
        .map { alarms ->
            alarms.map { alarm -> fromAlarmEntity(alarm) }
        }

    fun getAlarmGroups(): Flow<List<AlarmGroupState>> = alarmGroupDao.getAll()
        .map { alarmGroups ->
            alarmGroups.map { alarmGroup -> fromAlarmGroupEntity(alarmGroup) }
        }

    fun getFilters(): Flow<List<Filter>> = filterDao.getAll()
        .map { filters ->
            filters.map { filter -> fromFilterEntity(filter) }
        }

    fun insert(alarm: AlarmState) {
        alarmDao.insert(toAlarmEntity(alarm))
    }

    fun insert(alarmGroup: AlarmGroupState) {
        alarmGroupDao.insert(toAlarmGroupEntity(alarmGroup))
    }

    fun insert(filter: Filter) {
        filterDao.insert(toFilterEntity(filter))
    }

    fun update(alarm: AlarmState) {
        alarmDao.update(toAlarmEntity(alarm))
    }

    fun update(alarmGroup: AlarmGroupState) {
        alarmGroupDao.update(toAlarmGroupEntity(alarmGroup))
    }

    fun update(filter: Filter) {
        filterDao.update(toFilterEntity(filter))
    }

    fun delete(alarm: AlarmState) {
        alarmDao.delete(toAlarmEntity(alarm))
    }

    fun delete(alarmGroup: AlarmGroupState) {
        alarmGroupDao.delete(toAlarmGroupEntity(alarmGroup))
    }

    fun delete(filter: Filter) {
        filterDao.delete(toFilterEntity(filter))
    }

    private fun fromAlarmEntity(value: AlarmEntity): AlarmState {
        TODO()
    }

    private fun toAlarmEntity(value: AlarmState): AlarmEntity {
        TODO()
    }

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