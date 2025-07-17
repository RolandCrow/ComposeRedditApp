package com.example.composeredditapp.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val defaultArgs: Bundle? = null
): AbstractSavedStateViewModelFactory(owner,defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return MainViewModel() as T
    }
}