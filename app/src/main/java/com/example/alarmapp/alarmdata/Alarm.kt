package com.example.alarmapp.alarmdata

import androidx.compose.runtime.snapshots.SnapshotStateList

data class Alarm (
    var hour: Int,                  // 시각(00 ~ 23)
    var minute: Int,                // 분(00 ~ 59)
    var repeatDays: SnapshotStateList<Boolean>, // 0: 월요일, 1: 화요일, ..., 6: 일요일(해당 요일에 반복함)
    var name: String = "",       // 알람 이름
    var groupName: String = "",     // 속한 그룹의 이름(빈 문자열의 경우 소속된 그룹 없음, 기본값: "")
    var bookmark: Boolean = false,   // 즐겨찾기(생성 시 꺼짐)
    var alarmSound: Boolean = true,       // 알람음
    var vibrate: Boolean = true,       // 진동
    var ringAgain: Boolean = true,       // 다시 울림
    var isOn: Boolean = true,       // 알람의 켜짐/꺼짐
    var updatedTime: Long,          // 알람이 생성/수정된 시스템 시간을 저장(사용하지 않는 알람 자동 삭제 시 사용)
    var weekTerm: Int = 1,          // n주마다 반복(기본값: 1(주마다 반복))
    var selectedRingtone : String = "선택 안함",
    var selectedVibrationPattern : String = "무음",
    var selectedRingAgain : String = "5분, 3회",
    var repeatGap : Int = 5,
    var repeatNumber: Int = 3,
    var gapCheckList: SnapshotStateList<Boolean>,
    var repeatCheckList :SnapshotStateList<Boolean>
) {
    // 다음은 Alarm 객체 생성 시에 임의로 결정할 수 없는 값
    val id: Int = getNextId()       // 알람 고유 식별자(생성 시 0부터 부여하며, 순차적으로 1씩 증가시킴)
    var ringCount: Int = 0          // 울린 횟수(n회 울린 알람을 삭제 시 사용, 기본값 :0)

    companion object {
        private var currentId: Int = 0

        private fun getNextId(): Int {
            return currentId++
        }
    }
}