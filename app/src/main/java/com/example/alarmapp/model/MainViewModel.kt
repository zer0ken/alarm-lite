package com.example.alarmapp.model

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.database.AlarmDatabase
import com.example.alarmapp.database.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    private val _isCleanupEnabled = mutableStateOf(false)
    var isCleanupEnabled: Boolean
        get() = _isCleanupEnabled.value
        set(value) {
            _isCleanupEnabled.value = value
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
        } else {
            scheduler.schedule(alarmState)
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

    fun getSelectedAlarms() =
        alarmStateMap.filter { it.value.isSelected }

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
            filterMap[it.title] = it
        }
    }

    private fun loadSortPreference(): String {
        return sharedPreferences.getString("selectedSort", "시간순") ?: "시간순"
    }

    private fun saveSortPreference(value: String) {
        sharedPreferences.edit().putString("selectedSort", value).apply()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(context) as T
        }
    }
}

@Composable
fun rememberMainViewModel(context: Context) = remember {
    MainViewModel(context)
}
