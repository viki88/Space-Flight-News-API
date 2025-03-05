package com.vikination.spaceflightnewsapp.di.modules

import android.content.Context
import com.vikination.spaceflightnewsapp.data.source.remote.Auth0ApiService
import com.vikination.spaceflightnewsapp.data.source.remote.SpaceFlightNewsApiService
import com.vikination.spaceflightnewsapp.data.repositories.AuthRepositoryImpl
import com.vikination.spaceflightnewsapp.data.repositories.RecentSearchRepositoryImpl
import com.vikination.spaceflightnewsapp.data.repositories.SpaceFlightNewsRepositoryImpl
import com.vikination.spaceflightnewsapp.data.source.local.dao.NewsDao
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import com.vikination.spaceflightnewsapp.domain.repositories.RecentSearchRepository
import com.vikination.spaceflightnewsapp.domain.repositories.SpaceFlightNewsRepository
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import com.vikination.spaceflightnewsapp.ui.utils.UserPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authManager: AuthManager,
        auth0ApiService: Auth0ApiService,
        userPrefs: UserPrefs
    ): AuthRepository =
        AuthRepositoryImpl( authManager, auth0ApiService, userPrefs)

    @Provides
    @Singleton
    fun provideSpaceFlightNewsRepository(
        spaceFlightNewsApiService: SpaceFlightNewsApiService,
        newsDao: NewsDao
    ): SpaceFlightNewsRepository = SpaceFlightNewsRepositoryImpl(
        spaceFlightNewsApiService,
        newsDao
    )

    @Provides
    @Singleton
    fun provideRecentSearchRepository(@ApplicationContext context: Context) :RecentSearchRepository =
        RecentSearchRepositoryImpl(context)

}