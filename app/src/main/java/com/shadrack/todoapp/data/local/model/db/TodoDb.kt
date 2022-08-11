package com.shadrack.todoapp.data.local.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shadrack.todoapp.data.local.model.dao.TodoDao
import com.shadrack.todoapp.data.local.model.entities.Todo
import com.shadrack.todoapp.util.Constants.DB_NAME

@Database(entities = [Todo::class], version = 1)
abstract class TodoDb: RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object{
        val databaseName = DB_NAME
        var todoDb: TodoDb? = null

        fun getDb(context: Context): TodoDb{
            if (todoDb == null){
                todoDb = Room.databaseBuilder(context,TodoDb::class.java,databaseName).build()
            }
            return todoDb!!
        }

    }

}