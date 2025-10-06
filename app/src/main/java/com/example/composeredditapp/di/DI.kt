package com.example.composeredditapp.di

import android.content.Context
import androidx.room.Room
import com.example.composeredditapp.database.AppDatabase
import com.example.composeredditapp.database.dbmapper.DbMapper
import com.example.composeredditapp.database.dbmapper.DbMapperImpl
import com.example.composeredditapp.repository.Repository
import com.example.composeredditapp.repository.RepositoryImpl

class DI(context: Context) {
    private val dbMapper: DbMapper = DbMapperImpl()
    private val database: AppDatabase by lazy { provideDatabase(context) }
    val repository: Repository by lazy { provideRepository(database) }

    private fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    private fun provideRepository(database: AppDatabase): Repository {
        val postDao = database.postDao()
        return RepositoryImpl(postDao,dbMapper)
    }
}