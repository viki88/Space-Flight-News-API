package com.vikination.spaceflightnewsapp.di.modules

import com.vikination.spaceflightnewsapp.di.LogoutWorkerFactory
import com.vikination.spaceflightnewsapp.di.WorkerKey
import com.vikination.spaceflightnewsapp.ui.utils.LogoutWorker
import com.vikination.spaceflightnewsapp.ui.utils.worker.ChildWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(LogoutWorker::class)
    abstract fun bindLogoutWorker(factory : LogoutWorkerFactory): ChildWorkerFactory
}