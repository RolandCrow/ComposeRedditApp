package com.example.composeredditapp.routing

import com.example.composeredditapp.R

sealed class Screen(val titleResId: Int, val route: String) {
    data object Home: Screen(R.string.home, "Home")
    data object Subscriptions: Screen(R.string.subreddits, "Subreddits")
    data object NewPost: Screen(R.string.new_post,"New Post")
    data object MyProfile: Screen(R.string.my_profile, "My Profile")
    data object ChooseCommunity: Screen(R.string.choose_community, "Choose a community")

    companion object {
        fun fromRoute(route: String?): Screen {
            return when(route) {
                Home.route -> Home
                Subscriptions.route -> Subscriptions
                NewPost.route -> NewPost
                MyProfile.route -> MyProfile
                ChooseCommunity.route -> ChooseCommunity
                else -> Home
            }
        }
    }
}