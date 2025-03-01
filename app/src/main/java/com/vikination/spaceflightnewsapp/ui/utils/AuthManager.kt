package com.vikination.spaceflightnewsapp.ui.utils

import android.app.Activity
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class AuthManager(private val account: Auth0) {

    fun login(activity: Activity, onSuccess: (Credentials) -> Unit, onError: (String) -> Unit){
        WebAuthProvider.login(account = account)
            .withScheme("app")
            .withScope("openid profile email")
//            .withTrustedWebActivity()
//            .withAudience(String.format("https://%s/userinfo", account.clientId))
            .start(activity, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    onError(error.getDescription())
                }

                override fun onSuccess(result: Credentials) {
                    onSuccess(result)
                }
            })
    }

    fun logout(activity: Activity, onSuccess: () -> Unit, onError: (String) -> Unit){
        WebAuthProvider.logout(account)
            .withScheme("app")
//            .withTrustedWebActivity()
            .start(activity, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    onError(error.getDescription())
                }

                override fun onSuccess(result: Void?) {
                    onSuccess()
                }
            })
    }
}