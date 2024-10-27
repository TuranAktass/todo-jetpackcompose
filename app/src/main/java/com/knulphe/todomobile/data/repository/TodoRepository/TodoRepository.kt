package com.knulphe.todomobile.data.repository.TodoRepository

import com.knulphe.todomobile.data.model.Statistics
import com.knulphe.todomobile.data.model.Task

interface TodoRepository {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun getCompletedTasks(): List<Task>
    suspend fun getStatistics(): Statistics
}