package com.example.composeredditapp.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.Switch
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.ToggleableStateKey
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.composeredditapp.R
import com.example.composeredditapp.screens.communities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val toggledSubredditIdKey = ActionParameters.Key<String>("ToggledSubredditIdKey")

class SubredditsWidget: GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .appWidgetBackground()
                    .background(Color.White)
                    .cornerRadius(16.dp)
            ) {
                WidgetTitle()
                ScrollableSubredditList()
            }
        }
    }

    @Composable
    fun WidgetTitle() {
        Text(
            text = "Subreddits",
            modifier = GlanceModifier.fillMaxWidth(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
        )
    }

    @Composable
    fun ScrollableSubredditList() {
        LazyColumn {
            items(communities) { communityId ->
                Subreddit(id = communityId)
            }
        }
    }

    @Composable
    fun Subreddit(@StringRes id: Int) {
        val preferences: Preferences = currentState()
        val checked: Boolean = preferences[booleanPreferencesKey(id.toString())] ?: false

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                provider = ImageProvider(R.drawable.subreddit_placeholder),
                contentDescription = null,
                modifier = GlanceModifier.size(24.dp)
            )
            Text(
                text = LocalContext.current.getString(id),
                modifier = GlanceModifier.padding(start = 16.dp),
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Switch(
                checked = checked,
                onCheckedChange = actionRunCallback<SwitchToggleAction>(
                    actionParametersOf(
                        toggledSubredditIdKey to id.toString()
                    )
                )
            )

        }
    }
}

class SwitchToggleAction: ActionCallback {
    private val Context.dataStore by preferencesDataStore("app_preferences")
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val toggledSubredditId: String = requireNotNull(parameters[toggledSubredditIdKey])
        val checked: Boolean = requireNotNull(parameters[ToggleableStateKey])

        updateAppWidgetState(context,glanceId) { glancePreference ->
            glancePreference[booleanPreferencesKey(toggledSubredditId)] = checked
        }

        context.dataStore.edit {  appPreference ->
            appPreference[booleanPreferencesKey(toggledSubredditId)] = checked
        }

        SubredditsWidget().update(context,glanceId)
    }
}

class SubredditWidgetReceived: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SubredditsWidget()
    private val coroutineScope = MainScope()
    private val Context.dataStore by preferencesDataStore("app_preferences")

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        coroutineScope.launch {
            val glanceId: GlanceId? = GlanceAppWidgetManager(context)
                .getGlanceIds(SubredditsWidget::class.java)
                .firstOrNull()

            if(glanceId != null) {
                withContext(Dispatchers.IO) {
                    context.dataStore.data
                        .map { preferences -> preferences.toSubredditIdToCheckedMap() }
                        .collect { subredditIdToCheckedMap ->
                            updateAppWidgetPreferences(subredditIdToCheckedMap,context,glanceId)
                            glanceAppWidget.update(context,glanceId)
                        }
                }
            }

        }
    }

    private suspend fun updateAppWidgetPreferences(
        subredditIdToCheckedMap:Map<Int, Boolean>,
        context: Context,
        glanceId: GlanceId
    ) {
        subredditIdToCheckedMap.forEach { (subredditId,checked) ->
            updateAppWidgetState(context,glanceId) { state->
                state[booleanPreferencesKey(subredditId.toString())] = checked
            }
        }
    }

    private fun Preferences.toSubredditIdToCheckedMap(): Map<Int, Boolean> {
        return communities.associateWith { communityId ->
            this[booleanPreferencesKey(communityId.toString())] ?: false
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        coroutineScope.cancel()
    }
}