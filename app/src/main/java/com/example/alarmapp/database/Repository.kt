package com.example.alarmapp.database

import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.database.alarm.AlarmEntity
import com.example.alarmapp.database.alarmgroup.AlarmGroupEntity
import com.example.alarmapp.database.filter.FilterEntity
import com.example.alarmapp.filter.FilterSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(private val db: AlarmDatabase) {
    private val alarmDao = db.alarmDao()
    private val alarmGroupDao = db.alarmGroupDao()
    private val filterDao = db.filterDao()

    fun getAlarms(): Flow<List<Alarm>> = alarmDao.getAll()
        .map { alarms ->
            alarms.map { alarm -> fromAlarmEntity(alarm) }
        }

    fun getAlarmGroups(): Flow<List<AlarmGroup>> = alarmGroupDao.getAll()
        .map { alarmGroups ->
            alarmGroups.map { alarmGroup -> fromAlarmGroupEntity(alarmGroup) }
        }

    fun getFilters(): Flow<List<FilterSet>> = filterDao.getAll()
        .map { filters ->
            filters.map { filter -> fromFilterEntity(filter) }
        }

    fun insert(alarm: Alarm) {
        alarmDao.insert(toAlarmEntity(alarm))
    }

    fun insert(alarmGroup: AlarmGroup) {
        alarmGroupDao.insert(toAlarmGroupEntity(alarmGroup))
    }

    fun insert(filter: FilterSet) {
        filterDao.insert(toFilterEntity(filter))
    }

    fun update(alarm: Alarm) {
        alarmDao.update(toAlarmEntity(alarm))
    }

    fun update(alarmGroup: AlarmGroup) {
        alarmGroupDao.update(toAlarmGroupEntity(alarmGroup))
    }

    fun update(filter: FilterSet) {
        filterDao.update(toFilterEntity(filter))
    }

    fun delete(alarm: Alarm) {
        alarmDao.delete(toAlarmEntity(alarm))
    }

    fun delete(alarmGroup: AlarmGroup) {
        alarmGroupDao.delete(toAlarmGroupEntity(alarmGroup))
    }

    fun delete(filter: FilterSet) {
        filterDao.delete(toFilterEntity(filter))
    }

    private fun fromAlarmEntity(value: AlarmEntity): Alarm {
        TODO()
    }

    private fun toAlarmEntity(value: Alarm): AlarmEntity {
        TODO()
    }

    private fun fromAlarmGroupEntity(value: AlarmGroupEntity) = AlarmGroup(
        value.name
    )

    private fun toAlarmGroupEntity(value: AlarmGroup) = AlarmGroupEntity(
        value.groupName
    )

    private fun fromFilterEntity(value: FilterEntity) = FilterSet(
        value.name,
        value.repeatFilter,
        value.groupFilter
    )

    private fun toFilterEntity(value: FilterSet) = FilterEntity(
        value.title,
        value.repeatFilter,
        value.groupFilter
    )
}