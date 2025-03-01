package com.vikination.spaceflightnewsapp.di.modules

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.auth0.BuildConfig
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuth0(@ApplicationContext context: Context): Auth0 =
        Auth0.getInstance(context)

    @Provides
    @Singleton
    fun auth0Manager(auth0: Auth0) :AuthManager = AuthManager(auth0)

}