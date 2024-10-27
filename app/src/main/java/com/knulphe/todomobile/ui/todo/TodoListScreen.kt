@file:OptIn(ExperimentalMaterial3Api::class)

package com.knulphe.todomobile.ui.todo

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ServerTimestamp
import com.knulphe.todomobile.data.model.Task
import com.knulphe.todomobile.view_model.TodoViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoListScreen(viewModel: TodoViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var newTask by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Todo") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
            }) {
                Icon(Icons.Rounded.Add, contentDescription = "")
            }
        },
    )
    {
        Column(modifier = Modifier.padding(32.dp)) {
            Spacer(modifier = Modifier.height(32.dp))
            TaskList(taskList = tasks, viewModel = viewModel)
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }
            ) {
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("New Todo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.addTask(Task(task = newTask))
                        newTask = ""
                        showBottomSheet = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) { Text("Add Task") }
            }
        }
    }
}

@Composable
fun TaskList(taskList: List<Task>, viewModel: TodoViewModel) {
    if (taskList.isEmpty()) {
        Text("Theres No Task")
    } else {
        LazyColumn {
            items(taskList, key = {it.taskId}) { task -> TaskItem(task, viewModel = viewModel) }
        }
    }
}

@Composable
fun TaskItem(task: Task, viewModel: TodoViewModel) {
    var isCompleted by remember { mutableStateOf(task.isCompleted) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)

    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = task.task)
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = isCompleted,
                onCheckedChange = { value ->
                    run {
                        isCompleted = true
                        task.isCompleted = value
                        task.completedAt = Date()
                        viewModel.updateTask(task)
                    }
                })
        }
    }
}