package com.example.alarmapp

sealed class Routes (val route: String) {
    object MainScreen: Routes("MainScreen")
    object Setting: Routes("Setting")
    object UpdateAlarm: Routes("UpdateAlarm?alarmId={%d}")
    object CreateAlarm: Routes("UpdateAlarm")
}