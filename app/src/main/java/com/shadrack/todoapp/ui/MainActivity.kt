package com.shadrack.todoapp.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadrack.todoapp.R
import com.shadrack.todoapp.data.local.model.db.TodoDb
import com.shadrack.todoapp.data.local.model.entities.Todo
import com.shadrack.todoapp.ui.adapter.TodoListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), TodoListAdapter.OnTodoItemClickListener {

    private var todoDb: TodoDb? = null
    private var todoListAdapter: TodoListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoDb = TodoDb.getDb(this)
        todoListAdapter = TodoListAdapter()
        todoListAdapter?.setTodoItemClickListener(this)

        floating_action_button.setOnClickListener {
            startActivity(Intent(this,AddTodoActivity::class.java))
        }


    }

    override fun onResume() {
        super.onResume()
        loadTodoList()
    }


    fun loadTodoList(){

        GlobalScope.launch {

            todoListAdapter?.todoList = todoDb?.todoDao()?.getAllTodoItems()


        }

        todo_recyclerview.adapter = todoListAdapter
        todo_recyclerview.layoutManager = LinearLayoutManager(this)
    }


    override fun onTodoItemLongClick(todo: Todo) {
        val alertDialog = AlertDialog.Builder(this)
            .setItems(R.array.dialog_items, DialogInterface.OnClickListener { dialogInterface, itemIndex ->
                if (itemIndex == 0){
                    val intent = Intent(this, AddTodoActivity::class.java)
                    intent.putExtra("todo_id",todo.id)
                    intent.putExtra("title",todo.title)
                    intent.putExtra("detail",todo.detail)
                    startActivity(intent)
                }else {

                    GlobalScope.launch {
                        todoDb?.todoDao()?.deleteTodo(todo)
                    }

                    GlobalScope.launch {
                        todoListAdapter?.todoList = todoDb?.todoDao()?.getAllTodoItems()
                    }

                    todo_recyclerview.adapter = todoListAdapter
                }
                dialogInterface.dismiss()
            })

            .create()
        alertDialog.show()

    }

    override fun onTodoItemClick(todo: Todo) {
        val intent = Intent(this, AddTodoActivity::class.java)
        intent.putExtra("todo_id",todo.id)
        intent.putExtra("title",todo.title)
        intent.putExtra("detail",todo.detail)
        startActivity(intent)
    }

}