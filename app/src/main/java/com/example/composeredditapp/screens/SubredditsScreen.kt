package com.example.composeredditapp.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composeredditapp.R
import com.example.composeredditapp.components.BackgroundText
import com.example.composeredditapp.model.SubredditModel

@Composable
fun SubredditsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = modifier.padding(16.dp),
            text = stringResource(R.string.recently_visited_subreddits),
            fontSize = 12.sp,
            style = MaterialTheme.typography.titleMedium
        )

        LazyRow(
            modifier = modifier.padding(end = 16.dp)
        ) {
            items(subreddits) {Subreddit(it)}
        }
        Communities(modifier)
    }
}

@Composable
fun Subreddit(subredditModel: SubredditModel, modifier: Modifier = Modifier) {
    Card(
       colors = CardColors(
           contentColor = MaterialTheme.colorScheme.primary,
           containerColor = MaterialTheme.colorScheme.background,
           disabledContentColor = MaterialTheme.colorScheme.background,
           disabledContainerColor =  MaterialTheme.colorScheme.background
       ),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .size(120.dp)
            .padding(
                start = 2.dp,
                end = 2.dp,
                top = 4.dp,
                bottom = 4.dp
            )
    ) {
        SubredditBody(subredditModel)
    }
}

@Composable
fun SubredditBody(
    subredditModel: SubredditModel, modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        val (backgroundImage,icon,name,members,description) = createRefs()

        SubredditImage(
            modifier = modifier.constrainAs(backgroundImage) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
            }
        )

        SubredditIcon(
            modifier = modifier.constrainAs(icon){
                top.linkTo(backgroundImage.bottom)
                bottom.linkTo(backgroundImage.bottom)
                centerHorizontallyTo(parent)
            }.zIndex(1f)
        )

        SubredditName(
            nameStringRes = subredditModel.name,
            modifier = modifier.constrainAs(name) {
                top.linkTo(icon.bottom)
                centerHorizontallyTo(parent)
            }
        )

        SubredditMembers(
            memberStringRes = subredditModel.members,
            modifier = modifier.constrainAs(members) {
                top.linkTo(name.bottom)
                centerHorizontallyTo(parent)
            }
        )

        SubredditDescription(
            descriptionStringRes = subredditModel.description,
            modifier = modifier.constrainAs(description) {
                top.linkTo(members.bottom)
                centerHorizontallyTo(parent)
            }
        )
    }
}

@Composable
fun SubredditImage(modifier: Modifier) {
    Image(
        painter = ColorPainter(MaterialTheme.colorScheme.surface),
        contentDescription = stringResource(id = R.string.subreddit_image),
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
    )
}

@Composable
fun SubredditIcon(modifier: Modifier) {
    Icon(
        modifier = modifier,
        tint = MaterialTheme.colorScheme.primary,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_planet),
        contentDescription = stringResource(id = R.string.subreddit_icon)
    )
}

@Composable
fun SubredditName(modifier: Modifier,@StringRes nameStringRes: Int) {
    Text(
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        text = stringResource(nameStringRes),
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp)
    )
}

@Composable
fun SubredditMembers(modifier: Modifier, @StringRes memberStringRes: Int) {
    Text(
        fontSize = 8.sp,
        text = stringResource(memberStringRes),
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
    )
}

@Composable
fun SubredditDescription(modifier: Modifier, @StringRes descriptionStringRes: Int) {
    Text(
        fontSize = 8.sp,
        text = stringResource(descriptionStringRes),
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp)
    )
}

@Composable
fun Communities(modifier: Modifier = Modifier) {
    mainCommunities.forEach {
        Community(text = stringResource(it), showToggle = true)
    }
    Spacer(modifier = modifier.height(4.dp))
    BackgroundText(stringResource(R.string.communities))
    communities.forEach {
        Community(text = stringResource(it), showToggle = true)
    }
}

@Composable
fun Community(
    text: String,
    modifier: Modifier = Modifier,
    showToggle: Boolean = false,
    onCommunityClicked: ()-> Unit = {}
) {
    var checked by remember { mutableStateOf(true) }
    val defaultRowModifier = modifier
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        .fillMaxWidth()

    val rowModifier = if(showToggle) {
        defaultRowModifier
            .toggleable(
                value = checked,
                onValueChange = {checked != checked},
                role = Role.Switch
            )
            .semantics{
                stateDescription = if(checked) {
                    "Subscribed"
                } else {
                    "Not subscribed"
                }
            }
    } else {
        defaultRowModifier.clickable{onCommunityClicked.invoke()}
    }

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
            contentDescription = null,
            modifier = modifier
                .size(24.dp)
                .clip(CircleShape)
        )
        Text(
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.primary,
            text = text,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        if(showToggle) {
            Switch(
                checked = checked,
                onCheckedChange = null
            )
        }
    }
}

val subreddits = listOf(
    SubredditModel(
        R.string.kodeco,
        R.string.members_120k,
        R.string.welcome_to_kodeco
    ),
    SubredditModel(
        R.string.programming,
        R.string.members_600k,
        R.string.hello_programmers
    ),
    SubredditModel(
        R.string.android,
        R.string.members_400k,
        R.string.welcome_to_android
    ),
    SubredditModel(
        R.string.androiddev,
        R.string.members_500k,
        R.string.hello_android_devs
    )
)

val mainCommunities = listOf(R.string.all, R.string.public_network)

val communities = listOf(
    R.string.digitalnomad,
    R.string.covid19,
    R.string.memes,
    R.string.humor,
    R.string.worldnews,
    R.string.dogs,
    R.string.cats
)