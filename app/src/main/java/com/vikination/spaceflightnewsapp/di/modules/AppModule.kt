package com.vikination.spaceflightnewsapp.di.modules

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.auth0.android.Auth0
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import com.vikination.spaceflightnewsapp.ui.utils.PermissionManager
import com.vikination.spaceflightnewsapp.ui.utils.UserPrefs
import com.vikination.spaceflightnewsapp.ui.utils.worker.ChildWorkerFactory
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
    fun auth0Manager(
        @ApplicationContext context: Context,
        auth0: Auth0,
        userPrefs: UserPrefs
    ) :AuthManager = AuthManager(context, auth0, userPrefs)

    @Provides
    @Singleton
    fun userPrefs(@ApplicationContext context: Context) :UserPrefs {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return UserPrefs(prefs)
    }

    @Provides
    @Singleton
    fun permissionManager(@ApplicationContext context: Context): PermissionManager =
        PermissionManager(context)

    @Provides
    @Singleton
    fun provideWorkerFactory(
        workerFactories: @JvmSuppressWildcards Map<Class<out ListenableWorker>, ChildWorkerFactory>
    ): WorkerFactory{
        return object :WorkerFactory(){
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker? {
                val foundEntry = workerFactories.entries.find {
                    Class.forName(workerClassName).isAssignableFrom(it.key)
                }
                return foundEntry?.value?.create(appContext, workerParameters)
            }
        }
    }
}