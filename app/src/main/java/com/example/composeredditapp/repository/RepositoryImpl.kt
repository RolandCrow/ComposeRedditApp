package com.example.composeredditapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composeredditapp.database.dao.PostDao
import com.example.composeredditapp.database.dbmapper.DbMapper
import com.example.composeredditapp.database.model.PostDbModel
import com.example.composeredditapp.model.PostModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val postDao: PostDao, private val mapper: DbMapper
): Repository {
    private var searchedText = ""
    private val allPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
        MutableLiveData<List<PostModel>>()
    }
    private val ownedPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
        MutableLiveData<List<PostModel>>()
    }

    init {
        initDatabase(this::updatePostLiveData)
    }
    override fun getAllPosts(): LiveData<List<PostModel>> {
       return allPostsLiveData
    }

    override fun getAllOwnedPosts(): LiveData<List<PostModel>> {
        return ownedPostsLiveData
    }

    override fun insert(postModel: PostModel) {
       postDao.insert(mapper.mapDbPost(postModel))
        updatePostLiveData()
    }

    override fun deleteAll() {
        postDao.deleteAll()
        updatePostLiveData()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initDatabase(postInitAction: () -> Unit) {
        GlobalScope.launch {
            val posts = PostDbModel.DEFAULT_POSTS.toTypedArray()
            val dbPosts = postDao.getAllPosts()
            if(dbPosts.isEmpty()) {
                postDao.insertAll(*posts)
            }
            postInitAction.invoke()
        }
    }

    override fun getAllSubreddits(searchedText: String): List<String> {
        this.searchedText = searchedText
        if(searchedText.isNotEmpty()) {
            return postDao.getAllSubreddits().filter { it.contains(searchedText) }
        }
        return postDao.getAllSubreddits()
    }

    private fun getAllPostsFromDatabase(): List<PostModel> =
        postDao.getAllPosts().map(mapper::mapPost)

    private fun getAllOwnedPostsFromDatabase(): List<PostModel> =
        postDao.getAllOwnedPosts("johndoe").map(mapper::mapPost)

    private fun updatePostLiveData() {
        allPostsLiveData.postValue(getAllPostsFromDatabase())
        ownedPostsLiveData.postValue(getAllOwnedPostsFromDatabase())
    }
}