package com.ftorrigo.todolist.data

import com.ftorrigo.todolist.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun insert(title: String, description: String?, id: Long?) {
        val entity = id?.let {
            dao.getById(it)?.copy(
                title = title,
                description = description
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false
        )
        dao.insert(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existingEntity = dao.getById(id) ?: return
        val updateEntity = existingEntity.copy(isCompleted = true)
        dao.insert(updateEntity)
    }

    override suspend fun delete(id: Long) {
        val existingEntity = dao.getById(id) ?: return
        dao.delete(existingEntity)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map {
            it.map { entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isCompleted = entity.isCompleted,
                )
            }
        }
    }

    override suspend fun getById(id: Long): Todo? {
        return dao.getById(id)?.let {
            Todo(
                id = it.id,
                title = it.title,
                description = it.description,
                isCompleted = it.isCompleted,
            )
        }
    }
}