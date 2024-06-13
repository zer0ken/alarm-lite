package com.example.alarmapp

sealed class Routes (val route: String, val slottedRoute: String? = null) {
    object MainScreen: Routes("MainScreen")
    object Setting: Routes("Setting")
    object UpdateAlarm: Routes("UpdateAlarm/{alarmId}", slottedRoute = "UpdateAlarm/%d")
    object CreateAlarm: Routes("CreateAlarm")
    object CreateAlarmInGroup: Routes("CreateAlarmInGroup/{groupName}", slottedRoute = "CreateAlarmInGroup/%s")
}