package com.example.composeredditapp.model

import com.example.composeredditapp.R

data class PostModel(
    val username: String,
    val subreddit: String,
    val title: String,
    val text: String,
    val likes: String,
    val comments: String,
    val type: PostType,
    val postedTime: String,
    val image: Int?
) {
    companion object {
        val DEFAULT_POST = PostModel(
            "johndoe",
            "androiddev",
            "Watch this awesome Jetpack Compose course!",
            "",
            "5614",
            "523",
            PostType.IMAGE,
            "4h",
            R.drawable.compose_course
        )

        val EMPTY = PostModel(
            "johndoe",
            "kodeco.com",
            "",
            "",
            "0",
            "0",
            PostType.TEXT,
            "0h",
            R.drawable.compose_course
        )
    }
}
