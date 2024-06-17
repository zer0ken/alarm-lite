package com.example.alarmapp.model

import java.util.Calendar

object AlarmComparator {

    // 상대적인 시간 기준 Comparator
    val relative: Comparator<AlarmState> = Comparator { a1, a2 ->
        // 다음 울릴 시간 계산
        val nextTriggerA1 = a1.getNextRingTime()
        val nextTriggerA2 = a2.getNextRingTime()

        // isOn 값을 우선적으로 고려
        when {
            a1.isOn && !a2.isOn -> -1
            !a1.isOn && a2.isOn -> 1
            else -> nextTriggerA1.compareTo(nextTriggerA2)
        }
    }

    // 절대적인 시간 기준 Comparator
    val absolute: Comparator<AlarmState> = Comparator { a1, a2 ->
        val hourCompare = a1.hour.compareTo(a2.hour)
        if (hourCompare == 0) {
            a1.minute.compareTo(a2.minute)
        } else {
            hourCompare
        }
    }
}

// 사용 예시
//fun main() {
//    val alarms = mutableListOf(
//        Alarm(content = "Morning Alarm", hour = 7, minute = 30, repeatDays = SnapshotStateList(), updatedTime = System.currentTimeMillis()),
//        Alarm(content = "Evening Alarm", hour = 18, minute = 0, repeatDays = SnapshotStateList(), updatedTime = System.currentTimeMillis(), isOn = true),
//        Alarm(content = "Night Alarm", hour = 22, minute = 45, repeatDays = SnapshotStateList(), updatedTime = System.currentTimeMillis())
//    )
//
//    // 절대 시간 기준
//    // 1. 알람의 켜짐/꺼짐은 고려하지 않음
//    // 2. 00시 00분이 가장 위, 23시 59분이 가장 아래
//    alarms.sortWith(AlarmComparator.absolute)
//    println("Sorted by hour and minute: $alarms")
//
//    // 상대 시간 기준
//    // 1. 켜진 알람을 우선으로 함
//    // 2. 현재 시간으로부터 (미래로) 가장 가까운 시간
//    alarms.sortWith(AlarmComparator.relative)
//    println("Sorted by next alarm time: $alarms")
//}
