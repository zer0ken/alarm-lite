package com.example.alarmapp

sealed class Routes (val route: String) {
    object MainScreen: Routes("MainScreen")
    object Setting: Routes("Setting")
    object AddUnitAlarm: Routes("AddUnitAlarm")
}