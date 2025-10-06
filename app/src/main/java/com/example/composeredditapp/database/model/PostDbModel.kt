package com.example.composeredditapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composeredditapp.R

@Entity
data class PostDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "subreddit") val subreddit: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "comments") val comments: Int,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "date_posted") val datePosted: Long,
    @ColumnInfo(name = "is_saved") val isSaved: Boolean
) {

    companion object {

        val DEFAULT_POSTS = listOf(
            PostDbModel(
                1,
                "johndoe",
                "androiddev",
                "Check out this new book about Jetpack Compose from Kodeco!",
                "Check out this new book about Jetpack Compose from Kodeco!",
                5614,
                523,
                0,
                System.currentTimeMillis(),
                false
            ),
            PostDbModel(
                2,
                "pro_dev",
                "digitalnomad",
                "My ocean view in Thailand.",
                "",
                2314,
                23,
                1,
                System.currentTimeMillis(),
                false
            ),
            PostDbModel(
                3,
                "johndoe",
                "programming",
                "Check out this new book about Jetpack Compose from Kodeco!",
                "Check out this new book about Jetpack Compose from Kodeco!",
                5214,
                423,
                0,
                System.currentTimeMillis(),
                false
            ),
            PostDbModel(
                4,
                "johndoe",
                "puppies",
                "My puppy running around the house looks so cute!",
                "My puppy running around the house looks so cute!",
                25315,
                1362,
                0,
                System.currentTimeMillis(),
                false
            ),
            PostDbModel(
                5,
                "ps_guy",
                "playstation",
                "My PS5 just arrived!",
                "",
                56231,
                823,
                0,
                System.currentTimeMillis(),
                false
            )
        )
    }
}