package com.vikination.spaceflightnewsapp.ui.utils

import android.content.SharedPreferences

class UserPrefs(val prefs :SharedPreferences) {

    fun saveUserLoggedIn() {
        val currentTime = System.currentTimeMillis()
        prefs.edit().putLong("last_login", currentTime).apply()
    }

    fun resetUserLoggedIn() {
        prefs.edit().putLong("last_login", 0L).apply()
    }

    private fun getLastActiveTime() :Long{
        return prefs.getLong("last_login", 0L)
    }

    fun hasSessionExpired(): Boolean {
        val lastActiveTime = getLastActiveTime()
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastActiveTime) > Constants.TIMER_INTERVAL_IN_MINUTES * 60 * 1000 // 10 minutes timeout
    }
}