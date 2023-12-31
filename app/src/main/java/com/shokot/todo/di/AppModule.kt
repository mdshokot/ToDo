package com.shokot.todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.shokot.todo.domain.dao.GraphDao
import com.shokot.todo.domain.dao.TaskDao
import com.shokot.todo.domain.dao.UserDao
import com.shokot.todo.domain.dao.UserTaskDao
import com.shokot.todo.domain.database.TodoDatabase
import com.shokot.todo.domain.repository.GraphRepository
import com.shokot.todo.domain.repository.TaskRepository
import com.shokot.todo.domain.repository.UserRepository
import com.shokot.todo.domain.repository.UserTaskRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context : Context) : TodoDatabase{
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "local_db"
        ).fallbackToDestructiveMigration().build()
    }

    // user
    @Provides
    @Singleton
    fun provideUserDao(db : TodoDatabase) : UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideUserRepository(dao : UserDao) : UserRepository = UserRepository(dao)
    //task
    @Provides
    @Singleton
    fun provideTaskDao(db: TodoDatabase):TaskDao = db.taskDao()
    @Provides
    @Singleton
    fun provideTaskRepository(dao : TaskDao) : TaskRepository = TaskRepository(dao)

    // UserTask
    @Provides
    @Singleton
    fun provideUserTaskDao(db: TodoDatabase): UserTaskDao = db.userTaskDao()

    @Provides
    @Singleton
    fun provideUserTaskRepository(dao: UserTaskDao): UserTaskRepository = UserTaskRepository(dao)

    //graph
    @Provides
    @Singleton
    fun provideGraphDao(db: TodoDatabase): GraphDao = db.graphDao()

    @Provides
    @Singleton
    fun provideGraphRepository(graphDao: GraphDao): GraphRepository = GraphRepository(graphDao)
}