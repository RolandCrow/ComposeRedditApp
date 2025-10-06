package com.example.composeredditapp.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.composeredditapp.R
import com.example.composeredditapp.routing.Screen
import com.example.composeredditapp.ui.theme.ComposeRedditThemeSettings


@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,
    onScreenSelected: (Screen) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        AppDrawerHeader()
        AppDrawerBody(onScreenSelected)
        AppDrawerFooter(modifier)
    }
}

@Composable
private fun AppDrawerHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Filled.AccountCircle,
            colorFilter = ColorFilter.tint(Color.LightGray),
            modifier = Modifier
                .padding(16.dp)
                .size(50.dp),
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            contentDescription = stringResource(R.string.account)
        )

        Text(
            text = stringResource(R.string.default_username),
            color = MaterialTheme.colorScheme.primary
        )
        ProfileInfo()
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.surface.copy(alpha = .2f),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    )
}

@Composable
fun ProfileInfo(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )  {
        val (karmaItem,divider,ageItem) = createRefs()
        val colors = MaterialTheme.colorScheme
        ProfileInfoItem(
            Icons.Filled.Star,
            R.string.default_karma_amount,
            R.string.karma,
            modifier = modifier.constrainAs(karmaItem) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start)
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .width(1.dp)
                .constrainAs(divider) {
                    centerVerticallyTo(karmaItem)
                    centerHorizontallyTo(parent)
                    height = Dimension.fillToConstraints
                },
            color = colors.primary.copy(alpha = .2f)
        )

        ProfileInfoItem(
            Icons.Filled.ShoppingCart,
            R.string.default_reddit_age_amount,
            R.string.reddit_age,
            modifier = modifier.constrainAs(ageItem) {
                start.linkTo(divider.end)
                centerVerticallyTo(parent)
            }
        )
    }
}


@Composable
private fun ProfileInfoItem(
    iconAsset: ImageVector,
    amountResourceId: Int,
    textResourceId: Int,
    modifier: Modifier
) {
    val colors = MaterialTheme.colorScheme
    ConstraintLayout(modifier = modifier) {
        val (iconRef,amountRef,titleRef) = createRefs()
        val itemModifier = Modifier

        Icon(
            contentDescription = stringResource(id = textResourceId),
            imageVector = iconAsset,
            tint = Color.Blue,
            modifier = itemModifier
                .constrainAs(iconRef) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp)
        )
        Text(
            text = stringResource(amountResourceId),
            color = colors.primary,
            fontSize = 10.sp,
            modifier = itemModifier
                .padding(start = 8.dp)
                .constrainAs(amountRef) {
                    top.linkTo(iconRef.top)
                    start.linkTo(iconRef.end)
                    bottom.linkTo(titleRef.top)
                }
        )
        Text(
            text = stringResource(textResourceId),
            color = Color.Gray,
            fontSize = 10.sp,
            modifier = itemModifier
                .padding(start = 8.dp)
                .constrainAs(titleRef) {
                    top.linkTo(amountRef.bottom)
                    start.linkTo(iconRef.end)
                    bottom.linkTo(iconRef.bottom)
                }
        )
    }
}

@Composable
private fun AppDrawerBody(
    onScreenSelected: (Screen) -> Unit
) {
    Column {
        ScreenNavigationButton(
            icon = Icons.Filled.AccountBox,
            label = stringResource(R.string.my_profile),
            onClickAction = {
                onScreenSelected.invoke(Screen.MyProfile)
            }
        )
        ScreenNavigationButton(
            icon = Icons.Filled.Home,
            label = stringResource(R.string.saved),
            onClickAction = {
                onScreenSelected.invoke(Screen.Subscriptions)
            }
        )
    }
}

@Composable
private fun ScreenNavigationButton(
    icon: ImageVector,
    label: String,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = colors.surface,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = onClickAction,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    colorFilter = ColorFilter.tint(Color.Gray),
                    contentDescription = null
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    fontSize = 10.sp,
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.primary
                )
            }
        }
    }
}

@Composable
private fun AppDrawerFooter(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            )
    ) {
        val colors = MaterialTheme.colorScheme
        val (settingsImage, settingsText,darkModeButton) = createRefs()

        Icon(
            modifier = modifier.constrainAs(settingsImage) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(R.string.settings),
            tint = colors.primary
        )
        Text(
            fontSize = 10.sp,
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.bodyMedium,
            color = colors.primary,
            modifier = modifier
                .padding(16.dp)
                .constrainAs(settingsText) {
                    start.linkTo(settingsImage.end)
                    centerVerticallyTo(settingsImage)
                }
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_moon),
            contentDescription = stringResource(R.string.change_theme),
            modifier = modifier
                .clickable(onClick = { changeTheme() })
                .constrainAs(darkModeButton) {
                    end.linkTo(parent.end)
                    bottom.linkTo(settingsImage.bottom)
                },
            tint = colors.primary
        )
    }
}

private fun changeTheme() {
    ComposeRedditThemeSettings.isInDarkTheme.value = ComposeRedditThemeSettings.isInDarkTheme.value.not()
}

@Preview
@Composable
private fun ProfileInfoItemPreview() {
    ProfileInfoItem(
        Icons.Filled.ShoppingCart,
        R.string.default_reddit_age_amount,
        R.string.reddit_age,
        Modifier
    )
}