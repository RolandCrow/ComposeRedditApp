package com.example.composeredditapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeredditapp.R
import com.example.composeredditapp.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

private const val SEARCH_DELAY_MILLIS = 300L

@Composable
fun ChooseCommunityScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onBackSelected: ()-> Unit
) {
    val scope = rememberCoroutineScope()
    val communities: List<String> by viewModel.subreddits.observeAsState(emptyList())
    var searchText by remember { mutableStateOf("") }
    var currentJob by remember { mutableStateOf<Job?>(null) }
    val activeColor = MaterialTheme.colorScheme.surface

    LaunchedEffect(Unit) {
        viewModel.searchCommunities(searchText)
    }

    Column {
        ChooseCommunityTopBar(onBackSelected = onBackSelected)
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                currentJob?.cancel()
                currentJob = scope.async {
                    delay(SEARCH_DELAY_MILLIS)
                    viewModel.searchCommunities(searchText)
                }
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search))
            },
            label = {Text(stringResource(R.string.search))},
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedLabelColor = activeColor,
                focusedContainerColor = activeColor,
            )
            )
        SearchedCommunities(communities,viewModel,modifier,onBackSelected)
    }
}

@Composable
fun SearchedCommunities(
    communities: List<String>,
    viewModel: MainViewModel?,
    modifier: Modifier = Modifier,
    onBackSelected: () -> Unit
) {
    communities.forEach {
        Community(
            text = it,
            modifier = modifier,
            onCommunityClicked =  {
                viewModel?.selectedCommunity?.postValue(it)
                onBackSelected.invoke()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCommunityTopBar(
    modifier: Modifier = Modifier,
    onBackSelected: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    TopAppBar(
        title = {
            Text(
                fontSize = 16.sp,
                text = stringResource(R.string.choose_community),
                color = colors.primary
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {onBackSelected.invoke()}
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = colors.primary,
                    contentDescription = stringResource(id = R.string.close)
                )
            }
        },
        modifier = modifier
            .height(48.dp)
            .background(Color.Blue),
    )
}