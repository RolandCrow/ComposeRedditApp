package com.example.composeredditapp.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeredditapp.R
import com.example.composeredditapp.ui.theme.ColorPrimary
import com.example.composeredditapp.ui.theme.ComposeRedditAppTheme

class PostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ComposeRedditApp)
        setContent {
            ComposeRedditAppTheme {
                PostScreen { finish() }
            }
        }
    }
}

@Composable
fun PostScreen(onBackClicked: () -> Unit = {}) {
    val hostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {SnackbarHost(hostState)},
        topBar = {TopBar { onBackClicked }}
    ) { paddingValues ->
        Content(paddingValues = paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onBackClicked: () -> Unit) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text(
                text = "Post",
                color = ColorPrimary,
                modifier = Modifier.clearAndSetSemantics{}
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    tint = Color.LightGray,
                    contentDescription = "Navigate up"
                )
            }
        },
    )
}

@Composable
private fun Content(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        TitleSection()
        SectionVerticalSpacer()
        AuthorSection()
        SectionVerticalSpacer()
        ContentSection()
        SectionVerticalSpacer()
        CommentsSection()
    }
}



@Composable
private fun TitleSection() {
    Text(
        text = "Check out this new book about Jetpack Compose from Kodeco!",
        color = ColorPrimary,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .semantics{heading()}
    )
}

@Composable
private fun AuthorSection() {
    SectionDescriptor("Author")
    Row(
        modifier = Modifier
            .padding(start = 16.dp)
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                "r/androiddev",
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
            )
            Text(
                "Posted by u/johndoe - 1d",
                color = Color.Gray
            )
        }
    }
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
        color =  ColorPrimary,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
    Text(
        text = text,
        color =  ColorPrimary,
        fontSize = 12.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}

