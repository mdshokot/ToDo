package com.shokot.todo.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shokot.todo.domain.convertors.DateConvertor
import com.shokot.todo.domain.convertors.ImageConvertor
import com.shokot.todo.domain.dao.GraphDao
import com.shokot.todo.domain.dao.TaskDao
import com.shokot.todo.domain.dao.UserDao
import com.shokot.todo.domain.dao.UserTaskDao
import com.shokot.todo.domain.entity.Graph
import com.shokot.todo.domain.entity.Task
import com.shokot.todo.domain.entity.User
import com.shokot.todo.domain.entity.UserTask

@Database(entities = [User::class,Task::class,UserTask::class,Graph::class],version = 4, exportSchema = true)
@TypeConverters(ImageConvertor::class,DateConvertor::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun taskDao() : TaskDao
    abstract fun userTaskDao() : UserTaskDao
    abstract fun graphDao() : GraphDao
}