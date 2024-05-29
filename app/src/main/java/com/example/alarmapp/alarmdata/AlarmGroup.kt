package com.example.alarmapp.alarmdata

import com.example.alarmapp.alarmdata.AlarmManager.alarmList

// 알람 그룹은 자신이 포함하고 있는 Alarm 객체를 알 수 없음
data class AlarmGroup (
    var groupName: String = "",     // 그룹 이름(그룹을 구분하는 식별자, 같은 그룹 이름이 존재할 수 없음)
    // var groupAlarmList: MutableList<Alarm> = mutableListOf(),   // 그룹에 속한 Alarm 객체들 (db 데이터 중복 -> 보류)
){
    // 그룹 이름이 같으면 같은 객체로 취급
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AlarmGroup) return false

        return groupName == other.groupName
    }
    override fun hashCode(): Int {
        return groupName.hashCode()
    }

    // 그룹 이름을 변경(이 그룹에 소속된 Alarm의 groupName도 모두 변경)
    fun changeGroupName(name: String) {
        alarmList.forEach { item ->
            if (item.groupName == this.groupName)
                item.groupName = name
        }
        groupName = name
    }

    // 이 그룹에 소속된 알람을 전부 켬
    fun turnOnAll() {
        alarmList.forEach { item ->
            if (item.groupName == this.groupName)
                item.isOn = true
        }
    }

    // 이 그룹에 소속된 알람을 전부 끔
    fun turnOffAll() {
        alarmList.forEach { item ->
            if (item.groupName == this.groupName)
                item.isOn = false
        }
    }
}