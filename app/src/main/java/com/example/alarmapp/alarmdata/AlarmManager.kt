package com.example.alarmapp.alarmdata

// 전반적인 알람의 관리를 담당하는 클래스(추가, 삭제, 편집 시 데이터의 무결성 보장)
// UI를 통한 모든 행동은 이 클래스를 통해 사용
// ex1. 알람 추가 버튼(+) 클릭 후 모든 설정을 마치고 확인 버튼을 누를 시 -> AlarmManager.addAlarm(a) (a는 alarm)
// ex2. 화면에 표시되는 첫 번째 알람 삭제 시 -> AlarmManager.removeAlarm(0)
object AlarmManager {
    private val alarmList = mutableListOf<Alarm>()          // 메인 화면에 표시되는 알람 List(이 순서가 "메인" 화면에 반영됨)
    private val alarmGroupSet = mutableSetOf<AlarmGroup>()  // 생성된 모든 그룹을 담는 Set(이 순서가 "드롭다운 리스트"에 반영됨, 그룹 이름이 같으면 동등한 그룹)

    // "알람 추가" 화면에서 최종적으로 "확인" 버튼 클릭 시 호출
    fun addAlarm(alarm: Alarm) {
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


    // "알람 그룹 추가" 화면에서 최종적으로 "확인(그룹 추가)" 버튼 클릭 시 호출
    fun addGroup(alarmGroup: AlarmGroup) {
        alarmGroupSet.add(alarmGroup)
    }

    // "알람 그룹 편집" 화면에서 최종적으로 "확인(그룹 이름 변경)" 버튼 클릭 시 호출(현재 알람 그룹 편집의 경우 알람 그룹 이름을 변경하는 기능뿐임)
    fun editGroup(alarmGroup: AlarmGroup, name: String) {
        alarmGroup.changeGroupName(name.trim())
    }
}