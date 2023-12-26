package com.shokot.todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.shokot.todo.domain.dao.UserDao
import com.shokot.todo.domain.database.TodoDatabase
import com.shokot.todo.domain.repository.UserRepository
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
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db : TodoDatabase) : UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideUserRepository(dao : UserDao) : UserRepository = UserRepository(dao)

}