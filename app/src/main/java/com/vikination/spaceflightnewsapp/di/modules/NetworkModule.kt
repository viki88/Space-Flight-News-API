package com.vikination.spaceflightnewsapp.di.modules

import com.vikination.spaceflightnewsapp.data.network.Auth0ApiService
import com.vikination.spaceflightnewsapp.data.network.SpaceFlightNewsApiService
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

    @Provides
    @Singleton
    fun provideCertificatePinnerNewsAPI() : CertificatePinner =
        CertificatePinner.Builder()
            .add("api.spaceflightnewsapi.net", "sha256/0QaROX1BpIov5Vvv71S5HQ/DKRT6wvQ1ez/kwSsj/sA=")
            .build()

    @Provides
    @Singleton
    fun provideSpaceFlightNewsApiService(certificatePinner: CertificatePinner): SpaceFlightNewsApiService =
        Retrofit.Builder()
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