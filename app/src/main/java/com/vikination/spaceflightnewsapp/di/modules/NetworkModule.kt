package com.vikination.spaceflightnewsapp.di.modules

import com.vikination.spaceflightnewsapp.data.source.remote.Auth0ApiService
import com.vikination.spaceflightnewsapp.data.source.remote.SpaceFlightNewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
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
    fun provideAuth0ApiService(): Auth0ApiService {
        CertificatePinner.Builder()
            .add("dev-ub3leimgvk0dnja2.us.auth0.com", "sha256/iUjmaqdLqYhF8roudwDZ3jE9X4F7LgA1srH9Rjd388A=")
            .build()

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

    @Provides
    @Singleton
    fun provideSpaceFlightNewsApiService(): SpaceFlightNewsApiService {
        val certificatePinner = CertificatePinner.Builder()
            .add(
                "api.spaceflightnewsapi.net",
                "sha256/0QaROX1BpIov5Vvv71S5HQ/DKRT6wvQ1ez/kwSsj/sA="
            )
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.spaceflightnewsapi.net/")
            .client(
                OkHttpClient.Builder()
                    .certificatePinner(certificatePinner)
                    .addNetworkInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SpaceFlightNewsApiService::class.java)
    }
}