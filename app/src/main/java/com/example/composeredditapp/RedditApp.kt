package com.example.composeredditapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composeredditapp.drawer.AppDrawer
import com.example.composeredditapp.routing.Screen
import com.example.composeredditapp.screens.AddScreen
import com.example.composeredditapp.screens.ChatActivity
import com.example.composeredditapp.screens.ChooseCommunityScreen
import com.example.composeredditapp.screens.HomeScreen
import com.example.composeredditapp.screens.MyProfileScreen
import com.example.composeredditapp.screens.SubredditsScreen
import com.example.composeredditapp.ui.theme.ComposeRedditAppTheme
import com.example.composeredditapp.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RedditApp(viewModel: MainViewModel) {
    ComposeRedditAppTheme {
        AppContent(viewModel)
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun AppContent(viewModel: MainViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Crossfade(targetState = navBackStackEntry?.destination?.route) { route ->

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(
                    onScreenSelected = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            },
            content = {
                Scaffold(
                    topBar = getTopBar(Screen.fromRoute(route), drawerState, coroutineScope),
                    bottomBar = {
                        BottomNavigationComponent(navController = navController)
                    },
                    content = {
                        MainScreenContainer(
                            navController = navController,
                            modifier = Modifier.padding(bottom = 56.dp),
                            viewModel = viewModel
                        )
                    }
                )
            }
        )
    }

}

fun getTopBar(
    screenState: Screen,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
): @Composable (() -> Unit) {
    if(screenState == Screen.MyProfile || screenState == Screen.ChooseCommunity) {
        return {}
    } else {
        return {
            TopAppBar(
                screen = screenState,
                drawerState = drawerState,
                coroutineScope = coroutineScope
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    screen: Screen,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    androidx.compose.material3.TopAppBar(
        title = {
            Text(
                text = stringResource(screen.titleResId),
                color = colors.primary
            )
        },
        colors = TopAppBarColors(
            containerColor = colors.background, titleContentColor = colors.primary,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            actionIconContentColor =Color.Transparent,
        ),
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch { drawerState.open() }
            }) {
                Icon(
                    Icons.Filled.AccountCircle,
                    tint = Color.LightGray,
                    contentDescription = stringResource(id = R.string.account)
                )
            }
        },
        actions = {
            if(screen == Screen.Home) {
                IconButton(
                    modifier = Modifier.semantics {
                        onClick(label = "Open Chat", action = null)
                    },
                    onClick = {context.startActivity(Intent(context, ChatActivity::class.java))}
                ) {
                    Icon(
                        Icons.Filled.MailOutline,
                        tint = Color.LightGray,
                        contentDescription = "Navigate to Chat"
                    )
                }
            }
        }
    )
}

@Composable
private fun MainScreenContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(viewModel)
            }
            composable(Screen.Subscriptions.route) {
                SubredditsScreen()
            }
            composable(Screen.NewPost.route) {
                AddScreen(viewModel, navController)
            }
            composable(Screen.MyProfile.route) {
                MyProfileScreen(viewModel) {navController.popBackStack() }
            }
            composable(Screen.ChooseCommunity.route) {
                ChooseCommunityScreen(viewModel) { navController }
            }
        }
    }
}

@Composable
private fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        NavigationItem(0, R.drawable.ic_baseline_home_24, R.string.home_icon, Screen.Home),
        NavigationItem(
            1,
            R.drawable.ic_baseline_format_list_bulleted_24,
            R.string.subscriptions_icon,
            Screen.Subscriptions
        ),
        NavigationItem(2, R.drawable.ic_baseline_add_24, R.string.post_icon, Screen.NewPost),
    )
    NavigationBar(modifier = modifier) {
        items.forEach {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = it.vectorResourcesId),
                        contentDescription = stringResource(id = it.contentDescriptionResourceId)
                    )
                },
                selected = selectedItem == it.index,
                onClick = {
                    selectedItem = it.index
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private data class NavigationItem(
    val index: Int,
    val vectorResourcesId: Int,
    val contentDescriptionResourceId: Int,
    val screen: Screen
)