package com.shadrack.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shadrack.todoapp.R
import com.shadrack.todoapp.data.local.model.entities.Todo

class TodoListAdapter(var todoList: List<Todo>? = ArrayList()): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    var onTodoItemClickListener: OnTodoItemClickListener? = null

    inner class TodoViewHolder(val view: View, val todoList: List<Todo>):RecyclerView.ViewHolder(view){

        fun bind(position: Int){
            if (itemCount != 0){
                view.findViewById<TextView>(R.id.tod_title).text = todoList.get(position).title
                view.findViewById<TextView>(R.id.todo_detail).text = todoList.get(position).detail
                view.findViewById<ImageView>(R.id.priority_imgView).setImageResource(getImage(todoList.get(position).priority))
            }
        }

        fun getImage(priority: Int): Int = if (priority == 1) R.drawable.low_priority else if( priority == 2) R.drawable.medium_priority else R.drawable.high_priority

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return TodoViewHolder(view, todoList!!)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.setOnLongClickListener {
            onTodoItemClickListener!!.onTodoItemLongClick(todoList!!.get(position))
            true
        }
        holder.view.setOnClickListener { onTodoItemClickListener?.onTodoItemClick(todoList!!.get(position)) }


        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return todoList!!.size
    }

    fun setTodoItemClickListener(onTodoItemClickListener: OnTodoItemClickListener){
        this.onTodoItemClickListener = onTodoItemClickListener
    }

    interface OnTodoItemClickListener{
        fun onTodoItemLongClick(todo: Todo)
        fun onTodoItemClick(todo: Todo)
    }

}