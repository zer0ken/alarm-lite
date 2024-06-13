package com.example.alarmapp.database

import android.content.Context


class Container(private val context: Context) {
    val repository: Repository by lazy {
        Repository(AlarmDatabase.getInstance(context))
    }
}