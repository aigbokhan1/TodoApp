package com.shadrack.todoapp.data.local.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
class Todo (@PrimaryKey(autoGenerate = true) var id : Int,
            @ColumnInfo(name = "todo_title") var title: String = "",
            var detail : String = "",
            var priority : Int)

