package com.vikination.spaceflightnewsapp.ui.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.vikination.spaceflightnewsapp.R
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import retrofit2.Retrofit

class AuthManager(
    private val context :Context,
    private val account: Auth0,
    private val userPrefs: UserPrefs
    ) {
    private val credentialManager = CredentialsManager(
        AuthenticationAPIClient(account),
        SharedPreferencesStorage(context)
    )
    private var tokenId = ""

    fun login(activity: Activity, onSuccess: (Credentials) -> Unit, onError: (AuthenticationException) -> Unit){
        WebAuthProvider.login(account = account)
            .withScheme("app")
            .withScope("openid profile email")
            .withTrustedWebActivity()
            .start(activity, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    onError(error)
                }

                override fun onSuccess(result: Credentials) {
                    credentialManager.saveCredentials(result)
                    tokenId = result.idToken
                    userPrefs.saveUserLoggedIn()
                    scheduleLogoutWorker(context)
                    onSuccess(result)
                }
            })
    }

    fun logout(activity: Activity, onSuccess: () -> Unit, onError: (AuthenticationException) -> Unit){
        WebAuthProvider.logout(account)
            .withScheme("app")
            .withTrustedWebActivity()
            .start(activity, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    onError(error)
                }

                override fun onSuccess(result: Void?) {
                    credentialManager.clearCredentials()
                    userPrefs.resetUserLoggedIn()
                    onSuccess()
                }
            })
    }

    fun logoutInBackground(context: Context, onSuccess: () -> Unit, onError: (AuthenticationException) -> Unit){
        WebAuthProvider
            .logout(account)
            .withScheme("app")
            .start(context, object :Callback<Void?, AuthenticationException>{
                override fun onFailure(error: AuthenticationException) {
                    onError(error)
                }

                override fun onSuccess(result: Void?) {
                    userPrefs.resetUserLoggedIn()
                    credentialManager.clearCredentials()
                    onSuccess()
                }
            })
    }

    fun getTokenId() = tokenId


}