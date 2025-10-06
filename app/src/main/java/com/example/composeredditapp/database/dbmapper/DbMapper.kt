package com.example.composeredditapp.database.dbmapper

import com.example.composeredditapp.database.model.PostDbModel
import com.example.composeredditapp.model.PostModel

interface DbMapper {
    fun mapPost(dbPostDbModel: PostDbModel): PostModel
    fun mapDbPost(postModel: PostModel): PostDbModel
}