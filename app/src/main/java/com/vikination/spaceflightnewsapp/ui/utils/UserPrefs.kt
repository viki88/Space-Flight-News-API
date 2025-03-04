package com.vikination.spaceflightnewsapp.ui.utils

import android.content.SharedPreferences

class UserPrefs(private val prefs :SharedPreferences) {

    fun saveUserLoggedIn(idToken :String) {
        val currentTime = System.currentTimeMillis()
        prefs.edit().putLong("last_login", currentTime).apply()
        prefs.edit().putString("id_token", idToken).apply()
    }

    fun resetUserLoggedIn() {
        prefs.edit().putLong("last_login", 0L).apply()
    }

    fun getIdToken() = prefs.getString("id_token", "")

    fun setLogoutStatus(isLogout :Boolean){
        prefs.edit().putBoolean("is_logout", isLogout).apply()
    }

    fun setAuthenticateStatus(isAuthenticate :Boolean){
        prefs.edit().putBoolean("is_authenticate", isAuthenticate).apply()
    }

    fun getAuthenticateStatus() = prefs.getBoolean("is_authenticate", false)

    fun getLogoutStatus() = prefs.getBoolean("is_logout", true)
}