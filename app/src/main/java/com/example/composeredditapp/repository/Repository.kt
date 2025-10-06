package com.example.composeredditapp.repository

import androidx.lifecycle.LiveData
import com.example.composeredditapp.model.PostModel

interface Repository {
    fun getAllPosts(): LiveData<List<PostModel>>
    fun getAllOwnedPosts(): LiveData<List<PostModel>>
    fun getAllSubreddits(searchedText: String): List<String>
    fun insert(postModel: PostModel)
    fun deleteAll()
}