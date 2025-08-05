package com.example.composeredditapp.screens

import android.graphics.Color
import android.graphics.drawable.Icon
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeredditapp.R
import com.example.composeredditapp.components.BackgroundText
import com.example.composeredditapp.model.SubredditModel

@Composable
fun SubredditsScreen() {

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
        Community(text = stringResource(it))
    }
    Spacer(modifier = modifier.height(4.dp))
    BackgroundText(stringResource(R.string.communities))
    communities.forEach {
        Community(text = stringResource(it))
    }
}

@Composable
fun Community(
    text: String,
    modifier: Modifier = Modifier,
    onCommunityClicked: ()-> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp,top=16.dp)
            .fillMaxWidth()
            .clickable {onCommunityClicked.invoke()}
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
            contentDescription = stringResource(id = R.string.community_icon),
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
        )
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