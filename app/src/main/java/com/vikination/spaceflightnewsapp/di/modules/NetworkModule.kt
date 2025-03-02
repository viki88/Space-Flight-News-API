package com.vikination.spaceflightnewsapp.di.modules

import com.vikination.spaceflightnewsapp.data.network.Auth0ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuth0ApiService():
            Auth0ApiService {
        return Retrofit.Builder()
            .baseUrl("https://dev-ub3leimgvk0dnja2.us.auth0.com/")
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Auth0ApiService::class.java)
    }
}