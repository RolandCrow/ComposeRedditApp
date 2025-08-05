package com.example.composeredditapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeredditapp.components.ImagePost
import com.example.composeredditapp.components.TextPost
import com.example.composeredditapp.model.PostModel
import com.example.composeredditapp.model.PostType
import com.example.composeredditapp.viewmodel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val posts: List<PostModel> by viewModel.allPosts.observeAsState(listOf())

    LazyColumn(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
    ) {
        items(posts) {
           if(it.type == PostType.TEXT) {
               TextPost(it)
           } else {
               ImagePost(it)
           }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }

}