package com.vikination.spaceflightnewsapp.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vikination.spaceflightnewsapp.data.source.local.entity.AuthorEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.EventEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.LaunchEntity

class NewsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromAuthorList(value: List<AuthorEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAuthorList(value: String): List<AuthorEntity> {
        val listType = object : TypeToken<List<AuthorEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromLaunchList(value: List<LaunchEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLaunchList(value: String): List<LaunchEntity> {
        val listType = object : TypeToken<List<LaunchEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromEventList(value: List<EventEntity>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toEventList(value: String): List<EventEntity> {
        val listType = object : TypeToken<List<EventEntity>>() {}.type
        return gson.fromJson(value, listType)
    }
}