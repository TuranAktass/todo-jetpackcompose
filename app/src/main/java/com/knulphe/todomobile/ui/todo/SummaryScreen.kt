package com.knulphe.todomobile.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.play.integrity.internal.f
import com.google.firebase.auth.FirebaseAuth
import com.knulphe.todomobile.data.model.Statistics
import com.knulphe.todomobile.helpers.PreferencesHelper
import com.knulphe.todomobile.view_model.SummaryViewModel

@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel,
    onLogout: () -> Unit,
    preferencesHelper: PreferencesHelper
) {
    val statistics = viewModel.statistics.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadStatistics();
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        StatisticsGrid(statistics = statistics.value)
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 16.dp),
            onClick = {
                FirebaseAuth.getInstance().signOut()
                preferencesHelper.clearUser()
                onLogout()
            }) {
            Text("Logout")
        }
    }
}

@Composable
fun StatisticsGrid(statistics: Statistics) {
    // Define your list of statistics to display in the grid
    val items = listOf(
        "Total Todos" to statistics.totalTodos,
        "Done Todos" to statistics.totalDoneTodos,
        "Waiting Todos" to statistics.totalWaitingTodos,
        "Daily Completed" to statistics.dailyCompletedTodos,
        "Weekly Completed" to statistics.weeklyCompletedTodos,
        "Monthly Completed" to statistics.monthlyCompletedTodos
    )

    LazyVerticalGrid(
        userScrollEnabled = true,
        columns = GridCells.Fixed(2), // 2 columns
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { (label, value) ->
            StatisticItem(label = label, value = value)
        }
    }


}

@Composable
fun StatisticItem(label: String, value: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value.toString(), fontSize = 24.sp)
        }
    }
}