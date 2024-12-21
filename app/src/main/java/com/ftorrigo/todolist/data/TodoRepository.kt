package com.ftorrigo.todolist.data

import com.ftorrigo.todolist.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insert(title: String, description: String?, id: Long? = null)

    suspend fun updateCompleted(id: Long, isCompleted: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Todo>>

    suspend fun getById(id: Long): Todo?
}