package com.example.composeredditapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.composeredditapp.components.Post
import com.example.composeredditapp.model.PostModel
import org.junit.Rule
import org.junit.Test

class PostTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun title_is_displayed() {
        val post = PostModel.DEFAULT_POST
        composeTestRule.setContent {
            Post(post = post)
        }
        composeTestRule.onNodeWithText(post.title).assertIsDisplayed()
    }

    @Test
    fun like_count_is_displayed() {
        val post = PostModel.DEFAULT_POST
        composeTestRule.setContent {
            Post(post = post)
        }
        composeTestRule.onNodeWithText(post.likes).assertIsDisplayed()
    }
}