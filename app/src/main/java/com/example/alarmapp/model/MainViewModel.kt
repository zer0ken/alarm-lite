package com.example.alarmapp.model

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.alarmapp.database.AlarmDatabase
import com.example.alarmapp.database.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    val alarmStateMap = mutableStateMapOf<Int, AlarmState>()
    val alarmGroupStateMap = mutableStateMapOf<String, AlarmGroupState>()
    val filterMap = mutableStateMapOf<String, Filter>()

    private val _isSelectMode: MutableState<Boolean> = mutableStateOf(false)
    var isSelectMode: Boolean
        get() = _isSelectMode.value
        set(value) {
            _isSelectMode.value = value
        }

    fun addGroup(name: String) {
        repository.insert(AlarmGroupState(name))
    }

    public suspend fun fetchAlarms() {
        repository.getAlarms().collect {
            alarmStateMap.clear()
            it.forEach {
                alarmStateMap.put(it.id, it)
            }
        }
    }

    public suspend fun fetchAlarmGroups() {
        repository.getAlarmGroups().collect {
            alarmGroupStateMap.clear()
            it.forEach {
                alarmGroupStateMap.put(it.groupName, it)
            }
        }
    }

    public suspend fun fetchFilter() {
        repository.getFilters().collect {
            filterMap.clear()
            it.forEach {
                filterMap.put(it.title, it)
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
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras

                return MainViewModel(
                    repository = Repository(AlarmDatabase.getInstance(application as Context))
                ) as T
            }
        }
    }
}