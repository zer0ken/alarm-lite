package com.example.alarmapp.alarmdata

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarmapp.addalarm.alarmsound.AlarmReceiver
import java.util.Calendar
import android.app.AlarmManager as AM


class AlarmViewModel : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>()
    val alarms: LiveData<List<Alarm>> get() = _alarms

    private val _alarmGroup = MutableLiveData<Map<String,AlarmGroup>>()
    val alarmGroup: LiveData<Map<String,AlarmGroup>> get() = _alarmGroup

    private val _alarmSoundUri = MutableLiveData<Uri?>()
    val alarmSoundUri: LiveData<Uri?> get() = _alarmSoundUri

    private val _vibrationPattern = MutableLiveData<String>()
    val vibrationPattern: LiveData<String> get() = _vibrationPattern

    var flag = 1 //1 신규 2 편집

    fun setAlarmSoundUri(uri: Uri?) {
        _alarmSoundUri.value = uri
    }

    fun getAlarmSoundUri(): Uri? {
        return _alarmSoundUri.value
    }

    fun setVibrationPattern(pattern: String) {
        _vibrationPattern.value = pattern
    }




    // 알람 필드
    private val alarmName = MutableLiveData<String>()
    private val hour = MutableLiveData<Int>()
    private val minute = MutableLiveData<Int>()
    private val groupName = MutableLiveData<String>()
    private val bookmark = MutableLiveData<Boolean>()
    private val weekTerm = MutableLiveData<Int>()
    private val isOn = MutableLiveData<Boolean>()
    private val repeatDays = MutableLiveData<SnapshotStateList<Boolean>>()
    private val alarmSound = MutableLiveData<Boolean>()
    private val vibrate = MutableLiveData<Boolean>()
    private val ringAgain= MutableLiveData<Boolean>()
    private val selectedRingtone= MutableLiveData<String>()
    private val selectedVibrationPattern= MutableLiveData<String>()
    private val selectedRingAgain= MutableLiveData<String>()
    private val repeatGap= MutableLiveData<Int>()
    private val repeatNumber= MutableLiveData<Int>()

    private val gapCheckList = MutableLiveData<SnapshotStateList<Boolean>>()
    private val repeatCheckList = MutableLiveData<SnapshotStateList<Boolean>>()

    /**
     * AlarmListView에서 사용하는 상태 정보 등을 저장하는 ViewModel입니다.
     *
     * @property _isSelectMode AlarmListView가 선택 모드인지를 나타내는 상태
     * @property _alarmStates 알람의 id에 AlarmState 객체를 대응시키는 상태 맵
     * @property _alarmGroupStates 알람 그룹의 groupName에 AlarmGroupState 객체를 대응시키는 상태 맵
     *
     * @author 이현령
     */
    private var _isSelectMode = mutableStateOf(false)

    private val _alarmStates = mutableStateMapOf<Int, AlarmState>()
    private val _alarmGroupStates = mutableStateMapOf<String, AlarmGroupState>()

    var isSelectMode
        get() = _isSelectMode.value
        set(value) {
            _isSelectMode.value = value
        }

    fun getAlarmState(id: Int) = _alarmStates[id] ?: AlarmState().also { _alarmStates[id] = it }
    fun getAlarmGroupState(groupName: String) =
        _alarmGroupStates[groupName] ?: AlarmGroupState().also { _alarmGroupStates[groupName] = it }
    /*** @author 이현령*/

    private fun makeAlarmDefault(){
        hour.value =  0
        minute.value =  0
        repeatDays.value = mutableStateListOf(false, false, false, false, false, false, false)
        alarmName.value = ""
        groupName.value = ""
        bookmark.value = false
        alarmSound.value = true
        vibrate.value = true
        ringAgain.value = true
        isOn.value = true
        weekTerm.value = 1
        selectedRingtone.value = "선택 안함"
        selectedVibrationPattern.value = "무음"
        selectedRingAgain.value = "5분, 3회"
        repeatGap.value = 5
        repeatNumber.value = 3
        gapCheckList.value = mutableStateListOf(true, false, false, false)
        repeatCheckList.value = mutableStateListOf(true, false, false)
    }

    fun editAlarm(alarm: Alarm){
        hour.value =  alarm.hour
        minute.value =  alarm.minute
        repeatDays.value = alarm.repeatDays
        alarmName.value = alarm.name
        groupName.value = alarm.groupName
        bookmark.value = alarm.bookmark
        alarmSound.value = alarm.alarmSound
        vibrate.value = alarm.vibrate
        ringAgain.value = alarm.ringAgain
        isOn.value = alarm.isOn
        weekTerm.value = alarm.weekTerm
        selectedRingtone.value = alarm.selectedRingtone
        selectedVibrationPattern.value =alarm.selectedVibrationPattern
        selectedRingAgain.value = alarm.selectedRingAgain
        repeatGap.value = alarm.repeatGap
        repeatNumber.value = alarm.repeatNumber
        gapCheckList.value = alarm.gapCheckList
        repeatCheckList.value = alarm.repeatCheckList
    }

    init {
        makeAlarmDefault()
        _alarms.value = AlarmManager.alarmList
        _alarmGroup.value = AlarmManager.alarmGroupMap
    }

    fun getAlarmList(): List<Alarm> {
        return _alarms.value?: emptyList()
    }

    fun setAlarmName(newAlarmName: String) {
        alarmName.value = newAlarmName
    }

    fun getAlarmName(): String {
        return alarmName.value!!
    }

    fun getAlarmGroup(): Map<String,AlarmGroup> {
        return _alarmGroup.value ?: emptyMap<String,AlarmGroup>()
    }

    fun setHour(newHour: Int) {
        hour.value = newHour
    }

    fun getHour(): Int {
        return hour.value!!
    }

    fun setMinute(newMinute: Int) {
        minute.value = newMinute
    }

    fun getMinute(): Int {
        return minute.value!!
    }

    fun setGroupName(newGroupName: String) {
        groupName.value = newGroupName
    }

    fun setBookmark(newBookmark: Boolean) {
        bookmark.value = newBookmark
    }

    fun getBookmark(): Boolean {
        return bookmark.value!!
    }

    fun setWeekTerm(newWeekTerm: Int) {
        weekTerm.value = newWeekTerm
    }

    fun setIsOn(newIsOn: Boolean) {
        isOn.value = newIsOn
    }

    fun setRepeatDays(newRepeatDays: SnapshotStateList<Boolean>) {
        repeatDays.value = newRepeatDays
    }

    fun getRepeatDays():SnapshotStateList<Boolean> {
        return repeatDays.value!!
    }

    fun setAlarmSound(newAlarmSound: Boolean) {
        alarmSound.value = newAlarmSound
    }

    fun getAlarmSound(): Boolean {
        return alarmSound.value!!
    }

    fun setVibrate(newVibrate: Boolean) {
        vibrate.value = newVibrate
    }

    fun getVibrate(): Boolean {
        return vibrate.value!!
    }

    fun setRingAgain(newRingAgain: Boolean) {
        ringAgain.value = newRingAgain
    }

    fun getRingAgain(): Boolean {
        return ringAgain.value!!
    }

    fun setSelectedRingtone(newSelectedRingtone: String) {
        selectedRingtone.value = newSelectedRingtone
    }

    fun getSelectedRingtone(): String {
        return selectedRingtone.value!!
    }

    fun setSelectedVibrationPattern(newSelectedVibrationPattern: String) {
        selectedVibrationPattern.value = newSelectedVibrationPattern
    }

    fun getSelectedVibrationPattern(): String {
        return selectedVibrationPattern.value!!
    }

    fun setSelectedRingAgain(newSelectedRingAgain: String) {
        selectedRingAgain.value = newSelectedRingAgain
    }

    fun getSelectedRingAgain(): String {
        return selectedRingAgain.value!!
    }

    fun setRepeatGap(newRepeatGap: Int) {
        repeatGap.value = newRepeatGap
    }

    fun getRepeatGap(): Int {
        return repeatGap.value!!
    }

    fun setRepeatNumber(newRepeatNumber: Int) {
        repeatNumber.value = newRepeatNumber
    }

    fun getRepeatNumber() : Int {
        return repeatNumber.value!!
    }

    fun setGapCheckList(newRepeatDays: SnapshotStateList<Boolean>) {
        gapCheckList.value = newRepeatDays
    }

    fun getGapCheckList():SnapshotStateList<Boolean> {
        return gapCheckList.value!!
    }

    fun setRepeatCheckList(newRepeatDays: SnapshotStateList<Boolean>) {
        repeatCheckList.value = newRepeatDays
    }

    fun getRepeatCheckList():SnapshotStateList<Boolean> {
        return repeatCheckList.value!!
    }

    // 새로운 알람을 생성하고 추가
    fun makeAlarm(context: Context) {
        val alarm = Alarm(
            hour = hour.value ?: 0,
            minute = minute.value ?: 0,
            repeatDays = repeatDays.value ?: mutableStateListOf(false, false, false, false, false, false, false) ,
            name = alarmName.value ?: "",
            groupName = groupName.value ?: "",
            bookmark = bookmark.value ?: false,
            alarmSound = alarmSound.value ?: true,
            vibrate = vibrate.value ?: true,
            ringAgain = ringAgain.value ?: true,
            isOn = isOn.value ?: true,
            updatedTime = System.currentTimeMillis(),
            weekTerm = weekTerm.value ?: 1,
            selectedRingtone = selectedRingtone.value ?: "선택 안함",
            selectedVibrationPattern = selectedVibrationPattern.value ?: "무음",
            selectedRingAgain = selectedRingAgain.value ?: "5분, 3회",
            repeatGap = repeatGap.value ?: 5,
            repeatNumber = repeatNumber.value ?: 3,
            gapCheckList =  gapCheckList.value ?: mutableStateListOf(true, false, false, false),
            repeatCheckList = repeatCheckList.value ?: mutableStateListOf(true, false, false)
        )
        makeAlarmDefault()
        AlarmManager.addAlarm(alarm)
        _alarms.value = AlarmManager.alarmList

        setAlarmRing(context,alarm.hour, alarm.minute)
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarmRing(context: Context, hour:Int, minute: Int){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AM
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_SOUND_URI", alarmSoundUri.value.toString())
            putExtra("VIBRATION_PATTERN", vibrationPattern.value)
            putExtra("REPEAT_GAP",repeatGap.value)
            putExtra("REPEAT_NUMBER", repeatNumber.value)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE,minute)
            set(Calendar.SECOND,0)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setExact(AM.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }


    // 기존의 알람 객체를 생성했다면, 이를 추가
    fun addAlarm(alarm: Alarm) {
        AlarmManager.addAlarm(alarm)
        _alarms.value = AlarmManager.alarmList
    }

    fun removeAlarm(id: Int) {
        AlarmManager.removeAlarm(id)
        _alarms.value = AlarmManager.alarmList
    }

    fun toggleOnOff(alarm: Alarm) {
        AlarmManager.toggleOnOff(alarm)
        _alarms.value = AlarmManager.alarmList
    }

    fun toggleBookmark(alarm: Alarm) {
        AlarmManager.toggleBookmark(alarm)
        _alarms.value = AlarmManager.alarmList
    }

    fun getAlarm(id: Int): Alarm? {
        return AlarmManager.getAlarm(id)
    }

    fun getAlarmFromIndex(index: Int): Alarm {
        return AlarmManager.getAlarmFromIndex(index)
    }

    fun removeAlarmFromIndex(index: Int) {
        AlarmManager.removeAlarmFromIndex(index)
        _alarms.value = AlarmManager.alarmList
    }

    fun removeAllAlarms() {
        AlarmManager.removeAllAlarm()
        _alarms.value = AlarmManager.alarmList
    }

    fun addGroup(groupName: String) {
        val alarmGroup = AlarmGroup(groupName)
        AlarmManager.addGroup(alarmGroup)
        _alarms.value = AlarmManager.alarmList
    }

    fun removeGroup(name: String) {
        AlarmManager.removeGroup(name)
        _alarms.value = AlarmManager.alarmList
    }

    fun turnOnAll(alarmGroup: AlarmGroup) {
        AlarmManager.turnOnAll(alarmGroup)
        _alarms.value = AlarmManager.alarmList
    }

    fun turnOffAll(alarmGroup: AlarmGroup) {
        AlarmManager.turnOffAll(alarmGroup)
        _alarms.value = AlarmManager.alarmList
    }

    fun sortAbsolute() {
        AlarmManager.sortAbsolute()
        _alarms.value = AlarmManager.alarmList
    }

    fun sortRelative() {
        AlarmManager.sortRelative()
        _alarms.value = AlarmManager.alarmList
    }
}


/**
 * AlarmItemView에서 사용하는 상태 정보 등을 저장하는 클래스입니다.
 *
 * @property _isSelected AlarmItemView가 선택된 상태인지를 나타내는 상태
 * @author 이현령
 */
class AlarmState {
    private val _isSelected: MutableState<Boolean> = mutableStateOf(false)

    var isSelected: Boolean
        get() = _isSelected.value
        set(value) {
            _isSelected.value = value
        }
}

/**
 * groupedAlarmItems에서 사용하는 상태 정보 등을 저장하는 클래스입니다.
 *
 * @property _isFolded groupedAlarmItems가 펼쳐진 상태인지를 나타내는 상태
 * @author 이현령
 */
class AlarmGroupState{
    private val _isFolded: MutableState<Boolean> = mutableStateOf(true)

    var isFolded: Boolean
        get() = _isFolded.value
        set(value) {
            _isFolded.value = value
        }
}