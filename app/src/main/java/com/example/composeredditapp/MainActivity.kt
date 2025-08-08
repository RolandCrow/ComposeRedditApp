package com.example.composeredditapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.composeredditapp.ui.theme.ComposeRedditAppTheme
import com.example.composeredditapp.viewmodel.MainViewModel
import com.example.composeredditapp.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels(factoryProducer = {
        MainViewModelFactory(
            this,
            (application as RedditApplication).di.repository
        )
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRedditAppTheme {
                RedditApp(viewModel)
            }
        }
    }
}

