package com.vikination.spaceflightnewsapp.ui.utils

import android.content.SharedPreferences

class UserPrefs(private val prefs :SharedPreferences) {

    fun saveUserLoggedIn() {
        val currentTime = System.currentTimeMillis()
        prefs.edit().putLong("last_login", currentTime).apply()
    }

    fun resetUserLoggedIn() {
        prefs.edit().putLong("last_login", 0L).apply()
    }
}