package com.vikination.spaceflightnewsapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class SpaceFlightNewsApplication :Application(), Configuration.Provider{

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                EntryPointAccessors.fromApplication(
                    this,
                    HiltWorkerFactoryEntryPoint::class.java
                ).getWorkerFactory()
            ).build()


}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HiltWorkerFactoryEntryPoint {
    fun getWorkerFactory(): WorkerFactory
}