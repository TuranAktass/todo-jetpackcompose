package com.knulphe.todomobile.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knulphe.todomobile.data.model.Statistics
import com.knulphe.todomobile.data.repository.TodoRepository.TodoRepository
import com.knulphe.todomobile.data.repository.TodoRepositoryImpl.TodoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SummaryViewModel(private val repository: TodoRepository = TodoRepositoryImpl()) :
    ViewModel() {
    private val _statistics = MutableStateFlow<Statistics>(Statistics())
    val statistics: StateFlow<Statistics> = _statistics

    fun loadStatistics() {
        viewModelScope.launch {
            _statistics.value = repository.getStatistics()
        }
    }
}