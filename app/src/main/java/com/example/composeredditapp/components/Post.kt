package com.example.composeredditapp.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeredditapp.R
import com.example.composeredditapp.model.PostModel

@Composable
fun TextPost(
    post: PostModel,
    onPostClicked: () -> Unit = {},
    onJoinButtonClick: (Boolean) -> Unit = {}
) {
    Post(post,onJoinButtonClick,onPostClicked) {
        TextContent(post.text)
    }
}

@Composable
fun ImagePost(
    post: PostModel,
    onPostClicked: () -> Unit = {} ,
    onJoinButtonClick: (Boolean) -> Unit = {},
    ) {
    Post(post, onJoinButtonClick, onPostClicked) {
        ImageContent(post.image ?: R.drawable.compose_course)
    }
}

@Composable
fun Post(
    post: PostModel,
    onJoinButtonClick: (Boolean) -> Unit = {},
    onPostClicked: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.large,
        onClick = {onPostClicked.invoke()},
        modifier = Modifier.semantics {
            customActions = listOf(
                CustomAccessibilityAction(
                    label = "Join",
                    action = {true}
                ),
                CustomAccessibilityAction(
                    label = "Save post",
                    action = {true}
                ),
                CustomAccessibilityAction(
                    label =  "Upvote",
                    action = {true}
                ),
                CustomAccessibilityAction(
                    label =  "Downvote",
                    action = {true}
                ),
                CustomAccessibilityAction(
                    label = "Navigate to comments",
                    action = {true}
                ),
                CustomAccessibilityAction(
                    label = "Share",
                    action = {true}
                ),
                CustomAccessibilityAction(
                    label = "Award",
                    action = {true}
                )

            )
        }
    ) {
        Column(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 8.dp
            )
        ) {
            Header(post,onJoinButtonClick)
            Spacer(modifier = Modifier.height(4.dp))
            content.invoke()
            Spacer(modifier = Modifier.height(8.dp))
            PostActions(post)
        }
    }
}

@Composable
fun Header(
    post: PostModel,
    onJoinButtonClick: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier.padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
            contentDescription = stringResource(id = R.string.subreddits),
            modifier = Modifier.size(40.dp).clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.subreddit_header,post.subreddit),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.post_header, post.username, post.postedTime),
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        JoinButton(onJoinButtonClick)
        MoreActionsMenu()
    }
    Title(text = post.title)
}

@Composable
fun MoreActionsMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart).clearAndSetSemantics{}) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.DarkGray,
                contentDescription = stringResource(id = R.string.more_actions)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            CustomDropdownMenuItem(
                vectorResourceId = R.drawable.ic_baseline_bookmark_24,
                text = stringResource(id = R.string.save)
            )
        }
    }
}

@Composable
fun CustomDropdownMenuItem(
    @DrawableRes vectorResourceId: Int,
    color: Color = Color.Black,
    text: String,
    onClickAction: ()-> Unit = {}
) {
    DropdownMenuItem(onClick = onClickAction, text ={
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(id = vectorResourceId),
                tint = color,
                contentDescription = stringResource(id = R.string.save)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontWeight = FontWeight.Medium, color = color)
        }
    })
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        minLines = 3,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

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
    val painter = painterResource(id = image)
    Image(
        painter = painterResource(image),
        contentDescription = stringResource(id = R.string.post_header_description),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun PostActions(post: PostModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp,)
            .clearAndSetSemantics{},
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VotingAction(text = post.likes, onUpVoteAction = {}, onDownVoteAction = {})
        PostAction(
            vectorResourceId = R.drawable.ic_baseline_comment_24,
            text = post.comments,
            actionContentDescription = "Navigate to comments",
            actionValueContentDescription = "${post.comments} Comments",
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.semantics{
            contentDescription = "Submission score"
        }
    ) {
        ArrowButton(
            onUpVoteAction,
            R.drawable.ic_baseline_arrow_upward_24,
            R.string.upvote
        )
        Text(
            text = text,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        ArrowButton(
            onDownVoteAction,
            R.drawable.ic_baseline_arrow_downward_24,
            R.string.downvote
        )
    }
}

@Composable
fun ArrowButton(onClickAction: () -> Unit, arrowResourceId: Int, contentDescriptionResourceId: Int) {
    IconButton(onClick = onClickAction, modifier = Modifier.size(30.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(arrowResourceId),
            contentDescription = stringResource(contentDescriptionResourceId),
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }
}

@Composable
fun PostAction(
    @DrawableRes vectorResourceId: Int,
    text: String,
    actionContentDescription: String? = null,
    actionValueContentDescription: String? = null,
    onClickAction: () -> Unit
) {
    Box(
        modifier = Modifier.clickable(
            onClick = onClickAction,
            onClickLabel = ""
        ).semantics {
            if (actionContentDescription != null) {
                contentDescription = actionContentDescription
            }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
               ImageVector.vectorResource(id = vectorResourceId),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.semantics{
                    if(actionValueContentDescription != null) {
                        contentDescription = actionValueContentDescription
                    }
                }
            )
        }
    }
}