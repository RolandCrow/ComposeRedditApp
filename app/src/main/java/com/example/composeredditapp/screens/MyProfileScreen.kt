package com.example.composeredditapp.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composeredditapp.R
import com.example.composeredditapp.components.PostAction
import com.example.composeredditapp.drawer.ProfileInfo
import com.example.composeredditapp.model.PostModel
import com.example.composeredditapp.routing.MyProfileRouter
import com.example.composeredditapp.routing.MyProfileScreenType
import com.example.composeredditapp.viewmodel.MainViewModel

private val tabNames = listOf(R.string.posts, R.string.about)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onBackSelected: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (topAppBar,tabs,bodyContent) = createRefs()
        val colors = MaterialTheme.colorScheme

        TopAppBar(
            title = {
                Text(
                    fontSize = 12.sp,
                    text = stringResource(R.string.default_username),
                    color = colors.primary
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {onBackSelected.invoke()}
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = colors.primary,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            modifier = modifier
                .constrainAs(topAppBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(48.dp)
                .background(Color.Blue)
        )

        MyProfileTabs(
            modifier = modifier.constrainAs(tabs) {
                top.linkTo(topAppBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Surface(
            modifier = modifier
                .constrainAs(bodyContent) {
                    top.linkTo(tabs.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 68.dp)
        ) {
            Crossfade(targetState = MyProfileRouter.currentScreen) { screen ->
                when(screen.value) {
                    MyProfileScreenType.Posts -> MyProfilePosts(modifier,viewModel)
                    MyProfileScreenType.About ->  MyProfileAbout()
                }
            }
        }
    }
}


@Composable
fun MyProfileTabs(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        tabNames.forEachIndexed { index,nameResource ->
            Tab(
                selected = index == selectedIndex,
                onClick = {
                    selectedIndex = index
                    changeScreen(index)
                }
            ) {
                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp,
                    text = stringResource(nameResource),
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp,)
                )
            }
        }
    }
}

private fun changeScreen(index: Int) {
    return when(index) {
        0 -> MyProfileRouter.navigateTo(MyProfileScreenType.Posts)
        else -> MyProfileRouter.navigateTo(MyProfileScreenType.About)
    }
}

@Composable
fun MyProfilePosts(modifier: Modifier,viewModel: MainViewModel) {
    val posts: List<PostModel> by viewModel.myPost.observeAsState(listOf())
    LazyColumn(
        modifier = modifier.background(color = MaterialTheme.colorScheme.secondary)
    ) {
        items(posts) {MyProfilePost(modifier,it)}
    }
}

@Composable
fun MyProfilePost(modifier: Modifier, postModel: PostModel) {
    Card(
        shape = MaterialTheme.shapes.large
    ) {
        ConstraintLayout(
            modifier = modifier.fillMaxSize()
        ) {
            val (redditIcon, subredditName,actionsBar,title,description, settingIcon) = createRefs()
            val postModifier = Modifier
            val colors = MaterialTheme.colorScheme

            Image(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(id = R.string.my_profile),
                modifier = postModifier
                    .size(20.dp)
                    .constrainAs(redditIcon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 8.dp, top = 8.dp)
            )

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_more_vert_24),
                contentDescription = stringResource(id = R.string.more_actions),
                modifier = postModifier
                    .size(20.dp)
                    .constrainAs(settingIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(end = 8.dp, top = 8.dp)
            )

            Text(
                text = "${postModel.username} • ${postModel.postedTime}",
                fontSize = 8.sp,
                modifier = postModifier
                    .constrainAs(subredditName) {
                        top.linkTo(redditIcon.top)
                        bottom.linkTo(redditIcon.bottom)
                        start.linkTo(redditIcon.end)
                    }
                    .padding(start = 2.dp, top = 8.dp)
            )

            Text(
                text = postModel.title,
                color = colors.primary,
                fontSize = 12.sp,
                modifier = postModifier
                    .constrainAs(title) {
                        top.linkTo(redditIcon.bottom)
                        start.linkTo(redditIcon.end)
                    }
                    .padding(start = 8.dp, top = 8.dp)
            )

            Text(
                text = postModel.text,
                color = Color.DarkGray,
                fontSize = 10.sp,
                modifier = postModifier
                    .constrainAs(description) {
                        top.linkTo(title.bottom)
                        start.linkTo(redditIcon.start)
                    }
                    .padding(start = 8.dp, top = 8.dp)
            )

            Row(
                modifier = postModifier
                    .fillMaxWidth()
                    .constrainAs(actionsBar) {
                        top.linkTo(description.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        end = 16.dp,
                        start = 16.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PostAction(
                    vectorResourceId = R.drawable.ic_baseline_arrow_upward_24,
                    text = postModel.likes,
                    onClickAction = {}
                )
                PostAction(
                    vectorResourceId = R.drawable.ic_baseline_comment_24,
                    text = postModel.comments,
                    onClickAction = {}
                )
                PostAction(
                    vectorResourceId = R.drawable.ic_baseline_share_24,
                    text = stringResource(R.string.share),
                    onClickAction = {}
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun MyProfileAbout() {
    Column {
        ProfileInfo()
        Spacer(modifier = Modifier.height(8.dp))
        BackgroundText(stringResource(R.string.trophies))

        val trophies = listOf(
            R.string.verified_email,
            R.string.gold_medal,
            R.string.top_comment
        )
        LazyColumn {
            items(trophies) {Trophy(text = stringResource(it))}
        }
    }
}

@Composable
fun ColumnScope.BackgroundText(text: String) {
    Text(
        fontWeight = FontWeight.Medium,
        text = text,
        fontSize = 10.sp,
        color = Color.DarkGray,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
            .fillMaxWidth()
            .align(Alignment.Start)
    )
}

@Composable
fun Trophy(text: String, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(16.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = modifier.width(16.dp))
        Image(
           bitmap = ImageBitmap.imageResource(id = R.drawable.trophy),
            contentDescription = stringResource(id = R.string.trophies),
            contentScale = ContentScale.Crop,
            modifier = modifier.size(24.dp)
        )
        Spacer(modifier = modifier.width(16.dp))
        Text(
            text = text, fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}