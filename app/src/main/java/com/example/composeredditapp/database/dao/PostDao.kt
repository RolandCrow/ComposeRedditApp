package com.example.composeredditapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composeredditapp.database.model.PostDbModel

@Dao
interface PostDao {
    @Query("SELECT * FROM POSTDBMODEL")
    fun getAllPosts(): List<PostDbModel>

    @Query("SELECT * FROM PostDbModel WHERE username=:username")
    fun getAllOwnedPosts(username: String):List<PostDbModel>

    @Query("SELECT  DISTINCT subreddit FROM postdbmodel")
    fun getAllSubreddits(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postDbModel: PostDbModel)

    @Query("DELETE FROM PostDbModel")
    fun deleteAll()

    @Insert
    fun insertAll(vararg postDbModels: PostDbModel)
}