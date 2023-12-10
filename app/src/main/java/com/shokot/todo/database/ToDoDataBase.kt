package com.shokot.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shokot.todo.database.entity.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class ToDoDataBase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        @Volatile
        var INSTANCE: ToDoDataBase? = null
        fun getDatabase(context: Context): ToDoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ToDoDataBase::class.java,
                    "todo_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}