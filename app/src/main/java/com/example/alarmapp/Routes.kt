package com.example.alarmapp

sealed class Routes (val route: String, val slottedRoute: String? = null) {
    object MainScreen: Routes("MainScreen")
    object Setting: Routes("Setting")
    object UpdateAlarm: Routes("UpdateAlarm?alarmId={alarmId}", slottedRoute = "UpdateAlarm?alarmId={%d}")
    object CreateAlarm: Routes("UpdateAlarm?alarmId=-1")
}