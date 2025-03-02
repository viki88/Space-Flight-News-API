package com.vikination.spaceflightnewsapp.di.modules

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
        authManager: AuthManager
    ): AuthRepository =
        AuthRepositoryImpl( authManager)

}