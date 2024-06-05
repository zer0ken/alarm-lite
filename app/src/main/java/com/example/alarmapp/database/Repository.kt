package com.example.alarmapp.database

class Repository(private val db: AlarmDatabase) {
    private val alarmDao = db.alarmDao()
    private val alarmGroupDao = db.alarmGroupDao()
}