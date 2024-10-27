package com.knulphe.todomobile.view_model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.knulphe.todomobile.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class HomeViewTabs(val title: String, val icon: ImageVector) {
    TODO_LIST_SCREEN ("Todos", Icons.Filled.Edit),
    DONE("Done", Icons.Filled.Done),
    SUMMARY("Summary", Icons.Filled.Person)
}


class HomeViewModel : ViewModel() {

    private val _currentTabIndex = MutableStateFlow<HomeViewTabs>(HomeViewTabs.TODO_LIST_SCREEN)
    val currentTabIndex: StateFlow<HomeViewTabs> = _currentTabIndex

    fun toScreen(index: Int) {
        if (index == 0) {
            _currentTabIndex.value = HomeViewTabs.TODO_LIST_SCREEN
        } else if (index == 1) {
            _currentTabIndex.value = HomeViewTabs.DONE
        } else {
            _currentTabIndex.value = HomeViewTabs.SUMMARY
        }
    }


}