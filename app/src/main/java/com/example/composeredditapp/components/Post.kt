package com.example.composeredditapp.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeredditapp.R
import com.example.composeredditapp.model.PostModel

@Composable
fun TextContent(text: String) {
    Text(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp
        ),
        text = text,
        color = Color.Gray,
        fontSize = 12.sp,
        maxLines = 3
    )
}

@Composable
fun ImageContent(image: Int) {
    val imageAsset = ImageBitmap.imageResource(id=image)
    Image(
        bitmap = imageAsset,
        contentDescription = stringResource(id = R.string.post_header_description),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(imageAsset.width.toFloat() / imageAsset.height),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun PostActions(post: PostModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp,),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VotingAction(text = post.likes, onUpVoteAction = {}, onDownVoteAction = {})
        PostAction(
            vectorResourceId = R.drawable.ic_baseline_comment_24,
            text = post.comments,
            onClickAction = {}
        )
        PostAction(
            vectorResourceId = R.drawable.ic_baseline_share_24,
            text = stringResource(R.string.share),
            onClickAction = {}
        )
        PostAction(
            vectorResourceId = R.drawable.ic_baseline_emoji_events_24,
            text = stringResource(R.string.award),
            onClickAction = {}
        )
    }
}

@Composable
fun VotingAction(
    text: String,
    onUpVoteAction: () -> Unit,
    onDownVoteAction: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ArrowButton(onUpVoteAction, R.drawable.ic_baseline_arrow_upward_24)
        Text(
            text = text,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        ArrowButton(onDownVoteAction,R.drawable.ic_baseline_arrow_downward_24)
    }
}

@Composable
fun ArrowButton(onClickAction: () -> Unit, arrowResourceId: Int) {
    IconButton(onClick = onClickAction, modifier = Modifier.size(30.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(arrowResourceId),
            contentDescription = stringResource(id = R.string.upvote),
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }
}

@Composable
fun PostAction(
    @DrawableRes vectorResourceId: Int,
    text: String,
    onClickAction: () -> Unit
) {
    Box(modifier = Modifier.clickable(onClick = onClickAction)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
               ImageVector.vectorResource(id = vectorResourceId),
                contentDescription = stringResource(id = R.string.post_action),
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, fontWeight = FontWeight.Medium, color = Color.Gray)
        }
    }
}