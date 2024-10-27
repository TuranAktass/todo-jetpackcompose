package com.knulphe.todomobile.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knulphe.todomobile.data.model.Task
import com.knulphe.todomobile.data.repository.TodoRepository.TodoRepository
import com.knulphe.todomobile.data.repository.TodoRepositoryImpl.TodoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DoneTodoViewModel(private val repository: TodoRepository = TodoRepositoryImpl()) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

     fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getCompletedTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
            loadTasks()
        }
    }
}