package com.vikination.spaceflightnewsapp.di

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey (val value : KClass<out ListenableWorker>)