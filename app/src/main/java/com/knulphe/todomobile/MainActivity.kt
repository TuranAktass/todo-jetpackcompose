package com.knulphe.todomobile

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.FirebaseApp
import com.knulphe.todomobile.helpers.PreferencesHelper
import com.knulphe.todomobile.ui.HomeView
import com.knulphe.todomobile.ui.register.RegisterScreen
import com.knulphe.todomobile.ui.theme.TodoAppTheme
import com.knulphe.todomobile.view_model.DoneTodoViewModel
import com.knulphe.todomobile.view_model.HomeViewModel
import com.knulphe.todomobile.view_model.SummaryViewModel
import com.knulphe.todomobile.view_model.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        setContent {
            TodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp()
                }
            }
        }
    }
}

enum class Screen {
    LOGIN, REGISTER, HOME
}

@Composable
fun TodoApp() {
    val context = LocalContext.current
    val preferencesHelper = remember { PreferencesHelper(context) }
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

    LaunchedEffect(Unit) {
        if (preferencesHelper.isRemembered()) {
            currentScreen = Screen.HOME
        }
    }
    when (currentScreen) {
        Screen.LOGIN -> LoginScreen(
            onLoginSuccess = { currentScreen = Screen.HOME },
            onNavigateToRegister = { currentScreen = Screen.REGISTER },
            preferencesHelper = preferencesHelper
        )

        Screen.REGISTER -> RegisterScreen(
            onRegisterSuccess = { currentScreen = Screen.HOME }
        )

        Screen.HOME -> {
            val viewModel = TodoViewModel()
            val doneTodoViewModel = DoneTodoViewModel()
            val homeViewModel = HomeViewModel()
            val summaryViewModel = SummaryViewModel()
            HomeView(
                viewModel = homeViewModel,
                doneTodoViewModel = doneTodoViewModel,
                todoViewModel = viewModel,
                summaryTodoViewModel = summaryViewModel,
                onLogout = { currentScreen = Screen.LOGIN },
                preferencesHelper = preferencesHelper
            )
        }
    }
}
