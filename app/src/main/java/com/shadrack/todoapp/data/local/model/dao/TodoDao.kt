package com.shadrack.todoapp.data.local.model.dao

import androidx.room.*
import com.shadrack.todoapp.data.local.model.entities.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)
    @Query("SELECT * FROM todo_table WHERE id =:id")
    fun getTodoItem(id : Int): Todo

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTodoItems(): List<Todo>
}