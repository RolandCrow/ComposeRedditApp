package com.example.composeredditapp.database.dbmapper

import com.example.composeredditapp.R
import com.example.composeredditapp.database.model.PostDbModel
import com.example.composeredditapp.database.model.PostDbModel.Companion.DEFAULT_POSTS
import com.example.composeredditapp.model.PostModel
import com.example.composeredditapp.model.PostType
import java.util.concurrent.TimeUnit

class DbMapperImpl: DbMapper {
    override fun mapPost(dbPostDbModel: PostDbModel): PostModel {
        with(dbPostDbModel) {
            return PostModel(
                username = username,
                subreddit = subreddit,
                title = title,
                text = text,
                likes.toString(),
                comments = comments.toString(),
                type = PostType.fromType(type),
                postedTime = getPostedDate(datePosted),
                image = mapImage(dbPostDbModel.id!!)
            )
        }
    }

    private fun mapImage(id: Long): Int? =
        if (id == DEFAULT_POSTS[1].id) {
            R.drawable.thailand
        } else {
            null
        }

    override fun mapDbPost(postModel: PostModel): PostDbModel {
        with(postModel) {
            return PostDbModel(
                id = null,
                username = "johndoe",
                subreddit = subreddit,
                title = title,
                text = text,
                likes = 0,
                comments = 0,
                type = type.type,
                datePosted = System.currentTimeMillis(),
                isSaved = false,
            )
        }
    }

    private fun getPostedDate(date: Long): String {
        val hoursPassed =
            TimeUnit.HOURS.convert(System.currentTimeMillis() - date, TimeUnit.MILLISECONDS)
        if(hoursPassed > 24) {
            val dayPassed = TimeUnit.DAYS.convert(hoursPassed, TimeUnit.HOURS)
            if(dayPassed > 30) {
                if(dayPassed > 365) {
                    return (dayPassed / 365).toString() + "y"
                }
                return (dayPassed / 30).toString() + "mo"
            }
            return dayPassed.toString() + "d"
        }
        return hoursPassed.inc().toString() + "h"
    }
}