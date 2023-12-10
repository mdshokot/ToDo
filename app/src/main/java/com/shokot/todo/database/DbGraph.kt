package com.shokot.todo.database

import android.content.Context
import com.shokot.todo.database.repository.Repository

object DbGraph {

    lateinit var db : ToDoDataBase
        private set
    val repository by lazy {
        Repository(
            userDao = db.userDao
        )
    }

    fun provide(context : Context){
        db = ToDoDataBase.getDatabase(context)
    }

}