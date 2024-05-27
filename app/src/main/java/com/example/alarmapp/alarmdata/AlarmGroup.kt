package com.example.alarmapp.alarmdata

data class AlarmGroup (
    var groupName: String = "",     // 그룹 이름(그룹을 구분하는 key값, 같은 그룹 이름이 존재하면 안됨)
    var bookmark: Boolean = false   // 즐겨찾기(기본값: false)
){

}