package com.example.alarmapp.alarmdata

data class Alarm (
    var content: String = "",       // 알람 설명(기본값: "")
    var hour: Int,                  // 시각(00 ~ 23)
    var minute: Int,                // 분(00 ~ 59)
    var repeatDays: BooleanArray = BooleanArray(7), // 0: 월요일, 1: 화요일, ..., 6: 일요일(해당 요일에 반복함)
    var weekTerm: Int = 1,          // n주마다 반복(기본값: 1(주마다 반복))
    var isOn: Boolean = true,       // 알람의 켜짐/꺼짐 여부(기본값: true(생성 시 켜짐))
    var bookmark: Boolean = false,  // 즐겨찾기(기본값: false)
    var groupName: String = "",     // 속한 그룹의 이름(빈 문자열의 경우 소속된 그룹 없음, 기본값: "")
    var updatedTime: Long,          // 알람이 생성/수정된 시스템 시간을 저장(사용하지 않는 알람 자동 삭제 시 사용)
    var ringCount: Int = 0          // 울린 횟수(n회 울린 알람을 삭제 시 사용, 기본값 :0)
)