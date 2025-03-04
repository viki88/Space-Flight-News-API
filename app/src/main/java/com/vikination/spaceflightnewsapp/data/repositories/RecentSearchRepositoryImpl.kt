package com.vikination.spaceflightnewsapp.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.vikination.spaceflightnewsapp.data.utils.dataStore
import com.vikination.spaceflightnewsapp.domain.repositories.RecentSearchRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class RecentSearchRepositoryImpl(context : Context) :RecentSearchRepository {
    private val dataStore = context.dataStore

    override suspend fun getRecentSearches(): List<String> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.RECENT_SEARCHES] ?: emptyList()
        }.firstOrNull()?.toList() ?: emptyList()
    }

    override suspend fun addSearchQuery(query: String) {
        dataStore.edit { preferences ->
            val currentList = preferences[PreferencesKeys.RECENT_SEARCHES] ?: emptyList()
            val updatedList = (listOf(query) + currentList).distinct().take(10) // Store last 10 searches
            preferences[PreferencesKeys.RECENT_SEARCHES] = updatedList.toSet()
        }
    }

    override suspend fun clearSearchHistory() {
        dataStore.edit { it.remove(PreferencesKeys.RECENT_SEARCHES) }
    }

    private object PreferencesKeys {
        val RECENT_SEARCHES = stringSetPreferencesKey("recent_searches")
    }
}