package com.example.alarmapp.database

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.alarmapp.alarmdata.Alarm
import com.example.alarmapp.alarmdata.AlarmGroup
import com.example.alarmapp.database.alarm.AlarmEntity
import com.example.alarmapp.database.alarmgroup.AlarmGroupEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(private val db: AlarmDatabase) {
    private val alarmDao = db.alarmDao()
    private val alarmGroupDao = db.alarmGroupDao()

    fun getAlarms(): Flow<List<Alarm>> = alarmDao.getAll()
        .map { alarms -> alarms.map { alarm -> fromAlarmEntity(alarm) } }

    fun getAlarmGroups(): Flow<List<AlarmGroup>> = alarmGroupDao.getAll()
        .map { alarmGroups -> alarmGroups.map { alarmGroup -> fromAlarmGroupEntity(alarmGroup) } }

    fun insert(alarm: Alarm) {
        alarmDao.insert(toAlarmEntity(alarm))
    }

    fun insert(alarmGroup: AlarmGroup) {
        alarmGroupDao.insert(toAlarmGroupEntity(alarmGroup))
    }

    fun update(alarm: Alarm) {
        alarmDao.update(toAlarmEntity(alarm))
    }

    fun update(alarmGroup: AlarmGroup) {
        alarmGroupDao.update(toAlarmGroupEntity(alarmGroup))
    }

    fun delete(alarm: Alarm) {
        alarmDao.delete(toAlarmEntity(alarm))
    }

    fun delete(alarmGroup: AlarmGroup) {
        alarmGroupDao.delete(toAlarmGroupEntity(alarmGroup))
    }

    private fun fromAlarmEntity(alarmEntity: AlarmEntity): Alarm {
        TODO()
    }

    private fun toAlarmEntity(alarm: Alarm): AlarmEntity {
        TODO()
    }

    private fun fromAlarmGroupEntity(alarmGroupEntity: AlarmGroupEntity): AlarmGroup {
        TODO()
    }

    private fun toAlarmGroupEntity(alarmGroup: AlarmGroup): AlarmGroupEntity {
        TODO()
    }
}