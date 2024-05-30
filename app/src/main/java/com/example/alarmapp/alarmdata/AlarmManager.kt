package com.example.alarmapp.alarmdata

/*
전반적인 알람의 관리를 담당하는 클래스(추가, 삭제, 편집 시 데이터의 무결성을 보장해야 함)
최종 "확인" 버튼 등의 행동은 이 클래스를 통해 사용
ex1. 알람 추가 버튼(+) 클릭 후 모든 설정을 마치고 확인 버튼을 누를 시 -> AlarmManager.addAlarm(a) (a는 alarm)
ex2. 화면에 표시되는 첫 번째 알람 삭제 시 -> AlarmManager.removeAlarm(0)
ex3. 알람 그룹 "학교" 삭제 시 -> AlarmManager.removeGroup("학교")
*/

// 알람의 경우 List의 index를 통해 객체에 접근
// 그룹의 경우 getGroup(name)을 통해 객체에 접근
object AlarmManager {
    val alarmList = mutableListOf<Alarm>()          // 메인 화면에 표시되는 알람 List(이 순서가 "메인" 화면에 반영됨)
    val alarmGroupSet =
        mutableSetOf<AlarmGroup>()  // 생성된 모든 그룹을 담는 Set(이 순서가 "드롭다운 리스트"에 반영됨, 그룹 이름이 같으면 동등한 그룹)
    val alarmGroupMap =
        mutableMapOf<String, AlarmGroup>()   // 그룹 이름을 key, AlarmGroup을 value로 갖는 Map

    // alarmList의 index를 통해 "Alarm" 객체를 반환
    fun getAlarm(index: Int): Alarm {
        return alarmList[index]
    }

    // "알람 추가" 화면에서 최종적으로 "확인" 버튼 클릭 시 호출
    // 만약 추가하고자하는 "Alarm" 객체가 가진 그룹 이름이 alarmGroupSet에 존재하지 않는 경우, 해당 그룹 이름으로 새로운 그룹을 생성함
    fun addAlarm(alarm: Alarm) {
        val insertedAlarmGroup: AlarmGroup? = getGroup(alarm.groupName)
        if (insertedAlarmGroup == null) {
            addGroup(AlarmGroup(alarm.groupName))
        }
        alarmList.add(alarm)
    }

    // "알람 편집" 화면에서 최종적으로 "확인" 버튼 클릭 시 호출
    fun editAlarm(index: Int, alarm: Alarm) {
        alarmList[index] = alarm
    }

    // "메인" 화면에서 해당 알림 "삭제" 버튼 클릭 시 호출(삭제한 알람 객체 반환)
    fun removeAlarm(index: Int): Alarm {
        return alarmList.removeAt(index)
    }

    // "메인" 화면에서 "전체 삭제" 버튼 클릭 시 호출(되돌리기 기능을 위한 반환값 추가 예정)
    fun removeAllAlarm() {
        alarmList.clear()
    }

    // "메인" 화면에서 "ON/OFF" 스위치 클릭 시 호출
    fun toggleOnOff(index: Int) {
        val alarm: Alarm = alarmList[index]
        alarm.isOn = !alarm.isOn
    }

    // "메인" 화면에서 "즐겨찾기" 버튼 클릭 시 호출
    fun toggleBookmark(index: Int) {
        val alarm: Alarm = alarmList[index]
        alarm.bookmark = !alarm.bookmark
    }

    // 그룹 이름(key)를 통해 "AlarmGroup"(value) 객체를 반환
    // 해당 그룹 이름이 없으면 null 반환
    fun getGroup(name: String): AlarmGroup? {
        return alarmGroupMap.get(name)
    }

    // "알람 그룹 추가" 화면에서 최종적으로 "확인(그룹 추가)" 버튼 클릭 시 호출(그룹 이름이 같으면 Set에 추가, Map의 경우 value가 대체됨)
    fun addGroup(alarmGroup: AlarmGroup) {
        alarmGroupSet.add(alarmGroup)
        alarmGroupMap[alarmGroup.groupName] = alarmGroup
    }

    // "알람 그룹 편집" 화면에서 최종적으로 "확인(그룹 이름 변경)" 버튼 클릭 시 호출(현재 알람 그룹 편집의 경우 알람 그룹 이름을 변경하는 기능뿐임)
    fun editGroup(alarmGroup: AlarmGroup, name: String) {
        alarmGroup.changeGroupName(name.trim())
    }

    // "알람 그룹 삭제" 화면에서 최종적으로 "확인(삭제)" 버튼 클릭 시 호출
    // 삭제한 AlarmGroup 객체 반환, 해당 그룹 이름이 없으면 null 반환
    // 소속된 알람들이 있다면 모두 무소속으로 전환
    fun removeGroup(name: String): AlarmGroup? {
        // 기존 알람들의 소속 그룹을 모두 무소속으로 전환
        val removedGroup = getGroup(name)
        alarmList.forEach { item ->
            if (item.groupName == name)
                item.groupName = ""
        }
        // Set과 Map에서 삭제
        alarmGroupSet.remove(getGroup(name))
        alarmGroupMap.remove(name)
        return removedGroup
    }

    // 이 그룹에 소속된 알람을 전부 켬
    fun turnOnAll(alarmGroup: AlarmGroup) {
        alarmList.forEach { item ->
            if (item.groupName == alarmGroup.groupName)
                item.isOn = true
        }
    }

    // 이 그룹에 소속된 알람을 전부 끔
    fun turnOffAll(alarmGroup: AlarmGroup) {
        alarmList.forEach { item ->
            if (item.groupName == alarmGroup.groupName)
                item.isOn = false
        }
    }
}