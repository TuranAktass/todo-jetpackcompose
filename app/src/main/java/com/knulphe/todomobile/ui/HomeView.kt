package com.knulphe.todomobile.ui

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.knulphe.todomobile.helpers.PreferencesHelper
import com.knulphe.todomobile.ui.todo.DoneTodos
import com.knulphe.todomobile.ui.todo.SummaryScreen
import com.knulphe.todomobile.ui.todo.TodoListScreen
import com.knulphe.todomobile.view_model.DoneTodoViewModel
import com.knulphe.todomobile.view_model.HomeViewModel
import com.knulphe.todomobile.view_model.HomeViewTabs
import com.knulphe.todomobile.view_model.HomeViewTabs.*
import com.knulphe.todomobile.view_model.SummaryViewModel
import com.knulphe.todomobile.view_model.TodoViewModel

@Composable
fun HomeView(
    viewModel: HomeViewModel,
    todoViewModel: TodoViewModel,
    doneTodoViewModel: DoneTodoViewModel,
    summaryTodoViewModel: SummaryViewModel,
    preferencesHelper: PreferencesHelper,
    onLogout: () -> Unit,
) {
    val currentTabIndex by viewModel.currentTabIndex.collectAsState()


    Column {
        TabRow(selectedTabIndex = currentTabIndex.ordinal) {
            HomeViewTabs.values().forEachIndexed { index, tab ->
                Tab(
                    selected = currentTabIndex.ordinal == index,
                    onClick = { viewModel.toScreen(index) },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Row {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
        when (currentTabIndex) {
            TODO_LIST_SCREEN -> TodoListScreen(viewModel = todoViewModel)
            DONE -> DoneTodos(viewModel = doneTodoViewModel)
            SUMMARY -> SummaryScreen(
                viewModel = summaryTodoViewModel,
                onLogout = onLogout,
                preferencesHelper = preferencesHelper
            )
        }
    }
}