package com.example.composeredditapp.routing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class MyProfileScreenType {
    data object Posts: MyProfileScreenType()
    data object About: MyProfileScreenType()
}

data object MyProfileRouter {
    var currentScreen: MutableState<MyProfileScreenType> = mutableStateOf(MyProfileScreenType.Posts)

    fun navigateTo(destination: MyProfileScreenType) {
        currentScreen.value = destination
    }
}