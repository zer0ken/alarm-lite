package com.example.alarmapp.alarmdata

data class AlarmGroup (
    var groupName: String = "",     // 그룹 이름(그룹을 구분하는 key값, 같은 그룹 이름이 존재할 수 없음)
    var groupAlarmList: MutableList<Alarm> = mutableListOf(),   // 그룹에 속한 Alarm 객체들
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

    // 그룹 이름을 변경(포함하는 Alarm의 groupName도 모두 변경)
    fun changeGroupName(name: String) {
        groupName = name
        groupAlarmList.forEach { item ->
            item.groupName = name
        }
    }
}