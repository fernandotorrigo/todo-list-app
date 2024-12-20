package com.ftorrigo.todolist.domain

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
)

val todo1 = Todo(
    id = 1,
    title = "Todo 1",
    description = "Desc 1",
    isCompleted = false,
)
val todo2 = Todo(
    id = 1,
    title = "Todo 2",
    description = "Desc 2",
    isCompleted = true,
)
val todo3 = Todo(
    id = 3,
    title = "Todo 3",
    description = "Desc 3",
    isCompleted = false,
)