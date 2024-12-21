package com.ftorrigo.todolist.ui.feature.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ftorrigo.todolist.data.TodoDatabaseProvider
import com.ftorrigo.todolist.data.TodoRepositoryImpl
import com.ftorrigo.todolist.domain.Todo
import com.ftorrigo.todolist.domain.todo1
import com.ftorrigo.todolist.domain.todo2
import com.ftorrigo.todolist.domain.todo3
import com.ftorrigo.todolist.navigation.AddEditRoute
import com.ftorrigo.todolist.ui.UiEvent
import com.ftorrigo.todolist.ui.components.TodoItem
import com.ftorrigo.todolist.ui.theme.TodoListTheme

@Composable
fun ListScreen(
    navigateToAddEditItemScreen: (id: Long?) -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        dao = database.todoDao
    )
    val viewModel = viewModel<ListViewModel> {
        ListViewModel(todoRepository = repository)
    }

    val todos by viewModel.todos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is AddEditRoute -> {
                            navigateToAddEditItemScreen(uiEvent.route.id)
                        }
                    }
                }

                UiEvent.NavigateBack -> TODO()
                is UiEvent.ShowSnackbar -> TODO()
            }

        }
    }

    ListContent(
        todos = todos,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListContent(
    todos: List<Todo>,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ListEvent.AddEdit(null))
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onCompleteChange = {
                        onEvent(
                            ListEvent.CompleteChanged(
                                id = todo.id,
                                isCompleted = it
                            )
                        )
                    },
                    onItemClick = {
                        onEvent(ListEvent.AddEdit(id = todo.id))
                    },
                    onDeleteClick = {
                        onEvent(ListEvent.Delete(id = todo.id))
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun ListContentPreview(modifier: Modifier = Modifier) {
    TodoListTheme {
        ListContent(
            todos = listOf(
                todo1,
                todo2,
                todo3,
            ),
            onEvent = {}
        )
    }

}