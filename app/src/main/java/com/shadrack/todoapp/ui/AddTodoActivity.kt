package com.shadrack.todoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.shadrack.todoapp.R
import com.shadrack.todoapp.data.local.model.db.TodoDb
import com.shadrack.todoapp.data.local.model.entities.Todo
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTodoActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private var todoDb: TodoDb? = null
    private var priority = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        todoDb = TodoDb.getDb(this)

        radioGroup.setOnCheckedChangeListener(this)

        val title = intent.getStringExtra("title")
        val detail = intent.getStringExtra("detail")
        if (title.isNullOrEmpty() && detail.isNullOrEmpty()){
            add_todo.setOnClickListener {
                val todo = Todo(0,title_ed.text.toString(), detail_ed.text.toString(), priority)

                GlobalScope.launch {

                    todoDb?.todoDao()?.saveTodo(todo)
                }

                finish()
            }
        }else{
            add_todo.text = "Update"
            val todoID = intent.getIntExtra("todo_id",0)
            title_ed.setText(title)
            detail_ed.setText(detail)
            add_todo.setOnClickListener {
                val todo = Todo(todoID,title_ed.text.toString(), detail_ed.text.toString(), priority)

                GlobalScope.launch {
                    todoDb?.todoDao()?.updateTodo(todo)
                }

                finish()
            }
        }


    }

    override fun onCheckedChanged(radioGroup: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.low -> {
                priority = 1
            }
            R.id.medium -> {
                priority = 2
            }
            R.id.high -> {
                priority = 3
            }
        }
    }
}