package com.shokot.todo.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shokot.todo.domain.convertors.ImageConvertor
import com.shokot.todo.domain.dao.UserDao
import com.shokot.todo.domain.entity.User


@Database(entities = [User::class],version = 3, exportSchema = true)
@TypeConverters(ImageConvertor::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}