package com.pt.criticaltechworkschallenge.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    internal object Keys {
        val SELECTED_SOURCE_ID = stringPreferencesKey("selected_source_id")
        val SELECTED_SOURCE_NAME = stringPreferencesKey("selected_source_name")
    }

    val selectedSource: Flow<Pair<String, String>> = dataStore.data
        .map { preferences ->
            val sourceId = preferences[Keys.SELECTED_SOURCE_ID] ?: "bbc-news"
            val sourceName = preferences[Keys.SELECTED_SOURCE_NAME] ?: "BBC News"
            Pair(sourceId, sourceName)
        }

    suspend fun saveSourcePreference(sourceId: String, sourceName: String) {
        dataStore.edit { preferences ->
            preferences[Keys.SELECTED_SOURCE_ID] = sourceId
            preferences[Keys.SELECTED_SOURCE_NAME] = sourceName
        }
    }
}