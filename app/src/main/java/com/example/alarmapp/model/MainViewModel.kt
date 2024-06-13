package com.example.alarmapp.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.alarmapp.database.AlarmDatabase
import com.example.alarmapp.database.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val suspendScope = CoroutineScope(Dispatchers.IO)

    val alarmStateMap = mutableStateMapOf<Int, AlarmState>()
    val alarmGroupStateMap = mutableStateMapOf<String, AlarmGroupState>()
    val filterMap = mutableStateMapOf<String, Filter>()

    private val _isSelectMode: MutableState<Boolean> = mutableStateOf(false)
    var isSelectMode: Boolean
        get() = _isSelectMode.value
        set(value) {
            _isSelectMode.value = value
        }

    init {
        suspendScope.launch {
            fetchAlarms()
            fetchAlarmGroups()
            fetchFilter()
        }
    }

    fun updateAlarm(alarmState: AlarmState) {
        suspendScope.launch {
            if (alarmState.groupName.isNotBlank()) {
                addGroup(alarmState.groupName)
            }
            repository.insert(alarmState)
            fetchAlarmGroups()
            fetchAlarms()

            Log.d("MainViewModel", "created: $alarmState")
            Log.d("MainViewModel", "alarms: $alarmStateMap")
            Log.d("MainViewModel", "alarmGroups: $alarmGroupStateMap")
        }
    }

    fun addGroup(name: String) {
        val alarmGroupState = AlarmGroupState(name)
        alarmGroupStateMap[alarmGroupState.groupName] = alarmGroupState
        repository.insert(alarmGroupState)
    }

    private suspend fun fetchAlarms() {
        repository.getAlarms().collect {
            alarmStateMap.clear()
            it.forEach {
                alarmStateMap[it.id] = it
            }
        }
    }

    private suspend fun fetchAlarmGroups() {
        repository.getAlarmGroups().collect {
            alarmGroupStateMap.clear()
            it.forEach {
                alarmGroupStateMap[it.groupName] = it
            }
        }
    }

    private suspend fun fetchFilter() {
        repository.getFilters().collect {
            filterMap.clear()
            it.forEach {
                filterMap[it.title] = it
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewModel(
                    repository = Repository(AlarmDatabase.getInstance(application as Context))
                ) as T
            }
        }
    }
}