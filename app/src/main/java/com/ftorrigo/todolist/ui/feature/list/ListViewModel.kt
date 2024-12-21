package com.ftorrigo.todolist.ui.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftorrigo.todolist.data.TodoRepository
import com.ftorrigo.todolist.navigation.AddEditRoute
import com.ftorrigo.todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    val todos = todoRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.Delete -> {
                deleteTodo(event.id)
            }

            is ListEvent.CompleteChanged -> {
                completedChangedTodo(event.id, event.isCompleted)
            }

            is ListEvent.AddEdit -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(AddEditRoute(event.id)))
                }
            }
        }
    }

    private fun deleteTodo(id: Long) {
        viewModelScope.launch {
            todoRepository.delete(id)
        }
    }

    private fun completedChangedTodo(id: Long, isComplete: Boolean) {
        viewModelScope.launch {
            todoRepository.updateCompleted(id, isComplete)
        }
    }

}