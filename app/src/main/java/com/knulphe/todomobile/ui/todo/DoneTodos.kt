package com.knulphe.todomobile.ui.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.knulphe.todomobile.data.model.Task
import com.knulphe.todomobile.view_model.DoneTodoViewModel
import java.util.Date

@Composable
fun DoneTodos(viewModel: DoneTodoViewModel) {
    val tasks by viewModel.tasks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Column {
        LazyColumn {
            items(tasks) { task -> CompletedTaskItem(task, viewModel = viewModel) }
        }
    }
}

@Composable
fun CompletedTaskItem(task: Task, viewModel: DoneTodoViewModel) {
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
                        isCompleted = false
                        task.isCompleted = value
                        task.completedAt = null
                        viewModel.updateTask(task)
                    }
                })
        }
    }

}