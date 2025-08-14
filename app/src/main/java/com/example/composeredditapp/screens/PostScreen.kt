package com.example.composeredditapp.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeredditapp.R
import com.example.composeredditapp.ui.theme.ComposeRedditAppTheme

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ComposeRedditApp)
        setContent {
            ComposeRedditAppTheme {

            }
        }
    }
}

@Composable
fun PostScreen(onBackClicked: () -> Unit = {}) {

}

@Composable
private fun ContentSection() {
    SectionDescriptor(text = "Content")
    Text(
        text ="Check out this new book about Jetpack Compose from Kodeco! There is a new version with updated content and some new chapters!",
        color = darkColorScheme().primary,
        fontSize = 14.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun CommentsSection() {
    SectionDescriptor(text = "Comments")
    CommentVerticalSpacer()
    DummyContent(count = 1, text = stringResource(id = R.string.dummy_coment_1))
    CommentVerticalSpacer()
    DummyContent(count = 2, text = stringResource(id = R.string.dummy_coment_2))
    CommentVerticalSpacer()
    DummyContent(count = 3, text = stringResource(id = R.string.dummy_coment_3))
    CommentVerticalSpacer()
    DummyContent(count = 4, text = stringResource(id = R.string.dummy_coment_4))
    CommentVerticalSpacer()
    DummyContent(count = 5, text = stringResource(id = R.string.dummy_coment_5))
    CommentVerticalSpacer()
    DummyContent(count = 6, text = stringResource(id = R.string.dummy_coment_6))
    CommentVerticalSpacer()
    DummyContent(count = 7, text = stringResource(id = R.string.dummy_coment_7))
    CommentVerticalSpacer()
    DummyContent(count = 8, text = stringResource(id = R.string.dummy_coment_8))
}

@Composable
private fun SectionVerticalSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun CommentVerticalSpacer() {
    Spacer(modifier = Modifier.height(8.dp))
}



@Composable
private fun SectionDescriptor(text: String) {
    Text(
        text = text,
        color = Color.Gray,
        fontSize = 14.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .semantics{heading()}
    )
}

@Composable
private fun DummyContent(
    count: Int,
    text: String
) {
    Text(
        text = "User $count",
        color = Color.Black,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
    Text(
        text = text,
        color = Color.Black,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}

