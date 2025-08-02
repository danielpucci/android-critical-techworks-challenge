package com.pt.criticaltechworkschallenge.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(@param:ApplicationContext private val context: Context) {

    private object Keys {
        val SELECTED_SOURCE_ID = stringPreferencesKey("selected_source_id")
        val SELECTED_SOURCE_NAME = stringPreferencesKey("selected_source_name")
    }

    val selectedSource: Flow<Pair<String, String>> = context.dataStore.data
        .map { preferences ->
            val sourceId = preferences[Keys.SELECTED_SOURCE_ID] ?: "techcrunch"
            val sourceName = preferences[Keys.SELECTED_SOURCE_NAME] ?: "TechCrunch"
            Pair(sourceId, sourceName)
        }

    suspend fun saveSourcePreference(sourceId: String, sourceName: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.SELECTED_SOURCE_ID] = sourceId
            preferences[Keys.SELECTED_SOURCE_NAME] = sourceName
        }
    }
}