package com.ftorrigo.todolist.ui.feature

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ftorrigo.todolist.domain.Todo
import com.ftorrigo.todolist.domain.todo1
import com.ftorrigo.todolist.domain.todo2
import com.ftorrigo.todolist.domain.todo3
import com.ftorrigo.todolist.ui.components.TodoItem
import com.ftorrigo.todolist.ui.theme.TodoListTheme

@Composable
fun ListScreen(modifier: Modifier = Modifier) {

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListContent(todos: List<Todo>) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
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
                    onCompleteChange = {},
                    onItemClick = {},
                    onDeleteClick = {},
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
            )
        )
    }

}