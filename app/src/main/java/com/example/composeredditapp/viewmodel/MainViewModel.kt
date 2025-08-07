package com.example.composeredditapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeredditapp.model.PostModel
import com.example.composeredditapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
): ViewModel() {
    val allPosts by lazy { repository.getAllPosts() }
    val myPost by lazy { repository.getAllOwnedPosts() }
    val subreddits by lazy { MutableLiveData<List<String>>() }
    val selectedCommunity: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun searchCommunities(search: String){
        viewModelScope.launch(Dispatchers.Default) {
            subreddits.postValue(repository.getAllSubreddits(search))
        }
    }

    fun savePost(postModel: PostModel) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insert(postModel = postModel.copy(subreddit = selectedCommunity.value ?: ""))
        }
    }
}