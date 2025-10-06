package com.example.composeredditapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeredditapp.database.dao.PostDao
import com.example.composeredditapp.database.model.PostDbModel

@Database(entities = [PostDbModel::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "reddit_database"
    }

    abstract fun postDao(): PostDao
}