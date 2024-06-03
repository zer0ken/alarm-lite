package com.example.alarmapp.alarmdata

/*
전반적인 알람의 관리를 담당하는 클래스(추가, 삭제, 편집 시 데이터의 무결성을 보장해야 함)
※ alarmList와 alarmGroup에 직접 추가 및 삭제를 진행하지 말 것 (이 클래스에 정의된 함수를 사용해야 함, 알람 편집 시에는 getAlarm()을 통해 얻은 객체에서 값을 조정
ex1. 알람 추가 버튼(+) 클릭 후 모든 설정을 마치고 확인 버튼을 누를 시 -> AlarmManager.addAlarm(a) (a는 alarm)
ex2. 화면에 표시되는 첫 번째 알람 삭제 시 -> AlarmManager.removeAlarm(0)
ex3. 알람 그룹 "학교" 삭제 시 -> AlarmManager.removeGroup("학교")
*/

// 알람의 경우 List의 index를 통해 객체에 접근
// 그룹의 경우 getGroup(name)을 통해 객체에 접근
object AlarmManager {
    val alarmList = mutableListOf<Alarm>()          // 메인 화면에 표시되는 알람 List(이 순서가 "메인" 화면에 반영됨)
    val alarmGroupMap = mutableMapOf<String, AlarmGroup>()   // 그룹 이름을 key, AlarmGroup을 value로 갖는 Map

    // Alarm의 고유 id를 통해 객체 얻기
    // 해당 id를 가진 알람이 없다면 null 반환
    fun getAlarm(id: Int): Alarm? {
        return alarmList.find { it.id == id }
    }

    // alarmList의 index를 통해 객체 얻기
    fun getAlarmFromIndex(index: Int): Alarm {
        return alarmList[index]
    }

    // "알람 추가" 화면에서 최종적으로 "확인" 버튼 클릭 시 호출
    // 만약 추가하고자하는 "Alarm" 객체가 가진 그룹 이름이 alarmGroupSet에 존재하지 않는 경우, 해당 그룹 이름으로 새로운 그룹을 생성함
    fun addAlarm(alarm: Alarm) {
        if (getGroup(alarm.groupName) == null) {
            addGroup(AlarmGroup(alarm.groupName))
        }
        alarmList.add(alarm)
    }

    // Alarm의 고유 id를 통한 알람 삭제
    // 삭제된 "Alarm" 객체를 반환
    fun removeAlarm(id: Int): Alarm? {
        val alarm = alarmList.find { it.id == id }
        return if (alarm != null) {
            alarmList.remove(alarm)
            alarm
        } else {
            null
        }
    }

    // alarmList의 index를 통한 알람 삭제
    // 삭제된 "Alarm" 객체를 반환
    fun removeAlarmFromIndex(index: Int): Alarm {
        return alarmList.removeAt(index)
    }

    // "메인" 화면에서 "전체 삭제" 버튼 클릭 시 호출(되돌리기 기능을 위해 List를 복사하여 반환)
    fun removeAllAlarm(): List<Alarm> {
        val removedAlarms = alarmList.toList()
        alarmList.clear()
        return removedAlarms
    }

    // "메인" 화면에서 "ON/OFF" 스위치 클릭 시 호출
    fun toggleOnOff(alarm: Alarm) {
        alarm.isOn = !alarm.isOn
    }

    // "메인" 화면에서 "즐겨찾기" 버튼 클릭 시 호출
    fun toggleBookmark(alarm: Alarm) {
        alarm.bookmark = !alarm.bookmark
    }

    // 그룹 이름(key)를 통해 "AlarmGroup"(value) 객체를 반환
    // 해당 그룹 이름이 없으면 null 반환
    fun getGroup(name: String): AlarmGroup? {
        return alarmGroupMap.get(name)
    }

    // "알람 그룹 추가" 화면에서 최종적으로 "확인(그룹 추가)" 버튼 클릭 시 호출(그룹 이름이 같으면 Set에 추가, Map의 경우 value가 대체됨)
    fun addGroup(alarmGroup: AlarmGroup) {
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
        // Map에서 삭제
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

    // alarmList를 절대적인 시간 기준으로 정렬
    fun sortAbsolute() {
        alarmList.sortWith(AlarmComparator.absolute)
    }

    // alarmList를 상대적인 시간 기준으로 정렬 (켜져있는 것을 우선으로)
    fun sortRelative() {
        alarmList.sortWith(AlarmComparator.relative)
    }
}