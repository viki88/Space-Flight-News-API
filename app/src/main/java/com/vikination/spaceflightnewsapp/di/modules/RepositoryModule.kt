package com.vikination.spaceflightnewsapp.di.modules

import com.vikination.spaceflightnewsapp.data.network.Auth0ApiService
import com.vikination.spaceflightnewsapp.data.repositories.AuthRepositoryImpl
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authManager: AuthManager,
        auth0ApiService: Auth0ApiService
    ): AuthRepository =
        AuthRepositoryImpl( authManager, auth0ApiService)

}