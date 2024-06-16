package com.example.alarmapp.model

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.database.AlarmDatabase
import com.example.alarmapp.database.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class MainViewModel(private val repository: Repository) : ViewModel() {
    val alarmStateMap = mutableStateMapOf<Int, AlarmState>()
    val alarmGroupStateMap = mutableStateMapOf<String, AlarmGroupState>()
    val filterMap = mutableStateMapOf<String, Filter>()

    private val _isSelectMode: MutableState<Boolean> = mutableStateOf(false)

    init {
        fetchAll()
    }

    var isSelectMode: Boolean
        get() = _isSelectMode.value
        set(value) {
            _isSelectMode.value = value
        }

    fun updateAlarm(alarmState: AlarmState) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateAlarm(alarmState)
        }
    }
    private suspend fun _updateAlarm(alarmState: AlarmState) {
        if (alarmState.groupName.isNotBlank() && alarmGroupStateMap[alarmState.groupName] == null) {
            _addGroup(alarmState.groupName)
        }
        repository.insert(alarmState)
        fetchAlarms()
    }

    fun updateAlarms(alarmStates: List<AlarmState>) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateAlarms(alarmStates)
        }
    }

    private suspend fun _updateAlarms(alarmStates: List<AlarmState>) {
        alarmStates.forEach {
            if (it.groupName.isNotBlank() && alarmGroupStateMap[it.groupName] == null) {
                _addGroup(it.groupName)
            }
            repository.insert(it)
        }
        fetchAlarms()
    }

    fun addGroup(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _addGroup(name)
        }
    }

    private suspend fun _addGroup(name: String) {
        val alarmGroupState = AlarmGroupState(name)
        alarmGroupStateMap[alarmGroupState.groupName] = alarmGroupState
        repository.insert(alarmGroupState)
    }

    fun getAlarmInGroup(groupName: String) =
        alarmStateMap.filter { it.value.groupName == groupName }

    fun getSelectedAlarms() =
        alarmStateMap.filter { it.value.isSelected }

    fun fetchAll() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAlarmGroups()
            fetchAlarms()
            fetchFilter()
        }
    }

    private suspend fun fetchAlarms() {
        repository.getAlarms().forEach {
            val old = alarmStateMap[it.id]
            if (old != null) {
                it.isSelected = old.isSelected
            }
            alarmStateMap[it.id] = it
        }
    }

    private suspend fun fetchAlarmGroups() {
        repository.getAlarmGroups().forEach {
            alarmGroupStateMap[it.groupName] = it
        }
    }

    private suspend fun fetchFilter() {
        repository.getFilters().forEach {
            filterMap[it.name] = it
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(Repository(AlarmDatabase.getInstance(context))) as T
        }
    }

    val definedRepeatFilters: List<String> = listOf(
        "일요일마다",
        "월요일마다",
        "화요일마다",
        "수요일마다",
        "목요일마다",
        "금요일마다",
        "토요일마다",
    )

    val selectedGroupFilters = mutableStateListOf<String>()
    val selectedRepeatFiltersIndex = mutableStateListOf<Int>()

    var filterSetName: MutableState<String> = mutableStateOf("")
    var filterSetRepeatFilter = mutableStateListOf<Boolean>(
        false,  // 일
        false,  // 월
        false,  // 화
        false,  // 수
        false,  // 목
        false,  // 금
        false   // 토
    )
    var filterSetGroupFilter = mutableStateListOf<String>()

    fun deleteFilter(filter: Filter){
        viewModelScope.launch {
            repository.delete(filter)
//            getFilters()
        }
    }

    fun insertFilter(filter: Filter){
        viewModelScope.launch {
            repository.insert(filter)
//            getFilters()
        }
    }
}