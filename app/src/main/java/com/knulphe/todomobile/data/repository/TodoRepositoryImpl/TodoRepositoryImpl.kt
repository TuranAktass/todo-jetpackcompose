package com.knulphe.todomobile.data.repository.TodoRepositoryImpl

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.knulphe.todomobile.data.model.Statistics
import com.knulphe.todomobile.data.model.Task
import com.knulphe.todomobile.data.repository.TodoRepository.TodoRepository
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.time.temporal.ChronoUnit

class TodoRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : TodoRepository {

    private val userUid: String? get() = auth.currentUser?.uid

    override suspend fun getTasks(): List<Task> {
        return userUid?.let { uid ->
            db.collection(uid).whereEqualTo("completed", false).get()
                .await().documents.mapNotNull {
                    it.toObject(
                        Task::class.java
                    )
                }
        } ?: emptyList()
    }

    override suspend fun addTask(task: Task) {
        val newTaskId = db.collection(auth.uid!!).document().id
        task.taskId = newTaskId
        userUid?.let { uid -> db.collection(uid).document(newTaskId).set(task).await() }
    }

    override suspend fun updateTask(task: Task) {
        userUid?.let { uid ->
            db.collection(uid).document(task.taskId).set(task).await()
        }
    }

    override suspend fun getCompletedTasks(): List<Task> {
        return userUid?.let { uid ->
            db.collection(uid).whereEqualTo("completed", true).get()
                .await().documents.mapNotNull {
                    it.toObject(
                        Task::class.java
                    )
                }
        } ?: emptyList()
    }

    override suspend fun getStatistics(): Statistics {
        val statistics = Statistics();
        statistics.totalTodos = getTotalTodos() ?: 0
        statistics.dailyCompletedTodos = getDailyCompletedTodos() ?: 0
        statistics.totalDoneTodos = getTotalDoneTodos() ?: 0
        statistics.weeklyCompletedTodos = getWeeklyCompletedTodos() ?: 0
        statistics.monthlyCompletedTodos = getMonthlyCompletedTodos() ?: 0
        statistics.totalWaitingTodos = getTotalWaitingTodos() ?: 0

        return statistics;
    }

    private suspend fun getTotalTodos(): Int? {
        return userUid?.let { uid -> db.collection(uid).get().await().count() }
    }

    private suspend fun getTotalDoneTodos(): Int? {
        return userUid?.let { uid ->
            db.collection(uid).whereEqualTo("completed", true)
                .get().await().count()
        }
    }

    private suspend fun getTotalWaitingTodos(): Int? {
        return userUid?.let { uid ->
            db.collection(uid)
                .whereEqualTo("completed", false).get().await().count()
        }
    }

    private suspend fun getDailyCompletedTodos(): Int? {
        return userUid?.let { uid ->
            db.collection(uid).whereEqualTo("completed", true)
                .whereGreaterThan("completedAt", Timestamp(Instant.now().minus(1, ChronoUnit.DAYS)))
                .get()
                .await().size()
        }
    }

    private suspend fun getWeeklyCompletedTodos(): Int? {
        return userUid?.let { uid ->
            db.collection(uid).whereEqualTo("completed", true).whereGreaterThan(
                "completedAt", Timestamp(
                    Instant.now().minus(
                        7, ChronoUnit
                            .DAYS
                    )
                )
            ).get().await().size()
        }
    }

    private suspend fun getMonthlyCompletedTodos(): Int? {
        return userUid?.let { uid ->
            db.collection(uid).whereEqualTo("completed", true).whereGreaterThan(
                "completedAt", Timestamp(Instant.now().minus(30, ChronoUnit.DAYS))
            ).get().await().size()
        }
    }

}