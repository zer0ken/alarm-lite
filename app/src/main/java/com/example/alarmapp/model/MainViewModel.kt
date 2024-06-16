package com.example.alarmapp.model

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.database.AlarmDatabase
import com.example.alarmapp.database.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

@Stable
class MainViewModel(context: Context) : ViewModel() {
    private val repository = Repository(AlarmDatabase.getInstance(context))
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AlarmAppPreferences", Context.MODE_PRIVATE)

    val alarmStateMap = mutableStateMapOf<Int, AlarmState>()
    val alarmGroupStateMap = mutableStateMapOf<String, AlarmGroupState>()
    val filterMap = mutableStateMapOf<String, Filter>()

    private val _isSelectMode = mutableStateOf(false)
    var isSelectMode: Boolean
        get() = _isSelectMode.value
        set(value) {
            _isSelectMode.value = value
        }

    private val _is24HourView = mutableStateOf(false)
    var is24HourView: Boolean
        get() = _is24HourView.value
        set(value) {
            _is24HourView.value = value
        }

    private val _selectedSort = mutableStateOf(loadSortPreference())
    var selectedSort: String
        get() = _selectedSort.value
        set(value) {
            _selectedSort.value = value
            saveSortPreference(value)
        }

    private val scheduler = MainAlarmScheduler(context)

    init {
        fetchAll()
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
        if (!alarmState.isOn) {
            scheduler.cancel(alarmState)
        } else if (!scheduler.schedule(alarmState)) {
            alarmState.isOn = false
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
            scheduler.cancel(it)
            if (it.isOn) {
                scheduler.schedule(it)
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

    fun getSelectedAlarms() = alarmStateMap.filter { it.value.isSelected }

    fun clearAllSelections() {
        alarmStateMap.values.forEach { it.isSelected = false }
    }

    fun fetchAll() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAlarmGroups()
            fetchAlarms()
            fetchFilter()
        }
    }

    suspend fun fetchAlarms() {
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

    private fun loadSortPreference(): String {
        return sharedPreferences.getString("selectedSort", "시간순") ?: "시간순"
    }

    private fun saveSortPreference(value: String) {
        sharedPreferences.edit().putString("selectedSort", value).apply()
    }

    fun cleanupAlarms() {
        viewModelScope.launch(Dispatchers.IO) {
            val now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000
            val alarms = repository.getAlarms()
            alarms.forEach {
                if (!it.isOn && it.repeatOnWeekdays.all { weekday -> !weekday } ||
                    (it.expireDate != null && it.expireDate!! < now)
                ) {
                    repository.delete(it)
                    alarmStateMap.remove(it.id)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(context) as T
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
    val selectedFilterSet = mutableStateListOf<String>()

//    private val defaultFilterSetName = ""
    val defaultFilterSetRepeatFilter = mutableListOf(
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )
    private val defaultFilterSetGroupFilter = mutableListOf<String>()

    var filterSetName by mutableStateOf("")
    var filterSetRepeatFilter by mutableStateOf(defaultFilterSetRepeatFilter)
    var filterSetGroupFilter by mutableStateOf(defaultFilterSetGroupFilter)

    fun resetFilter() {
        filterSetName = ""
        filterSetRepeatFilter = defaultFilterSetRepeatFilter
        filterSetGroupFilter.clear()
    }


    fun updateFilter(filter: Filter) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateFilter(filter)
            fetchFilter()
        }
    }

    private suspend fun _updateFilter(filter: Filter) {
        if (filter.name.isNotBlank()) {
            if (filterMap.containsKey(filter.name)) {
                _updateExistingFilter(filter)
            } else {
                _addFilter(filter)
            }
        }
    }

    private suspend fun _addFilter(filter: Filter) {
        filterMap[filter.name] = filter
        repository.insert(filter)
    }

    private suspend fun _updateExistingFilter(filter: Filter) {
        filterMap[filter.name] = filter
        repository.update(filter)
    }


    fun deleteFilter(filter: Filter) {
        viewModelScope.launch {
            repository.delete(filter)
            filterMap.remove(filter.name)
            fetchFilter()
        }
    }

    fun insertFilter(filter: Filter) {
        viewModelScope.launch {
            repository.insert(filter)
            fetchFilter()
        }
    }

    fun getFilterByName(name: String?): Filter? {
        return filterMap[name]
    }

    fun toggleSelectAll(select: Boolean) {
        alarmStateMap.forEach { (_, alarmState) ->
            alarmState.isSelected = select
        }
    }

    fun deleteSelectedAlarms() {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedAlarms = alarmStateMap.filter { it.value.isSelected }.values.toList()
            selectedAlarms.forEach {
                _deleteAlarm(it)
                alarmStateMap.remove(it.id)
            }
            fetchAlarms()
        }
    }

    private suspend fun _deleteAlarm(alarmState: AlarmState) {
        repository.delete(alarmState)
    }

    fun OnOffSelectedAlarms(select: Boolean) {
        alarmStateMap.forEach { (_, alarmState) ->
            if (alarmState.isSelected) {
                alarmState.isOn = !select
            }
        }
    }

    fun clearGroupForSelectedAlarms() {
        alarmStateMap.forEach { (_, alarmState) ->
            if (alarmState.isSelected) {
                alarmState.groupName = ""
            }
        }
    }
}

@Composable
fun rememberMainViewModel(context: Context) = remember {
    MainViewModel(context)
}