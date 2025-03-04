package com.vikination.spaceflightnewsapp.domain.repositories

interface RecentSearchRepository{
    suspend fun getRecentSearches() :List<String>
    suspend fun addSearchQuery(query :String)
    suspend fun clearSearchHistory()

}