package com.example.composeredditapp.model

import androidx.annotation.StringRes
import com.example.composeredditapp.R

data class SubredditModel(
    @StringRes val name: Int,
    @StringRes val members:Int,
    @StringRes val description: Int
) {
    companion object {
        val DEFAULT_SUBREDDIT =
            SubredditModel(R.string.android, R.string.members_400k, R.string.welcome_to_android)
    }
}
