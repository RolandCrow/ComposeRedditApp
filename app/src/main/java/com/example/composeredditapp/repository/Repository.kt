package com.example.composeredditapp.repository

import androidx.lifecycle.LiveData
import com.example.composeredditapp.model.PostModel

interface Repository {
    fun getAllPosts(): LiveData<List<PostModel>>
    fun getAllOwnedPosts(): LiveData<List<PostModel>>
    fun insert(postModel: PostModel)
    fun deleteAll()
}