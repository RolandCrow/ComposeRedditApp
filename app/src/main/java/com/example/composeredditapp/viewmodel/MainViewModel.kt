package com.example.composeredditapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composeredditapp.repository.Repository

class MainViewModel(
    private val repository: Repository
): ViewModel() {
    val allPosts by lazy { repository.getAllPosts() }
    val myPost by lazy { repository.getAllOwnedPosts() }
}