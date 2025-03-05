package com.vikination.spaceflightnewsapp.di.modules

import android.content.Context
import androidx.room.Room
import com.vikination.spaceflightnewsapp.data.source.local.AppDatabase
import com.vikination.spaceflightnewsapp.data.source.local.dao.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) :AppDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "news_database"
        ).fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabase) :NewsDao{
        return appDatabase.newsDao()
    }
}