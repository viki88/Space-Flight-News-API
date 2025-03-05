package com.vikination.spaceflightnewsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vikination.spaceflightnewsapp.data.source.local.dao.NewsDao
import com.vikination.spaceflightnewsapp.data.source.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
@TypeConverters(NewsConverter::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun newsDao(): NewsDao
}