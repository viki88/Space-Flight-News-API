package com.vikination.spaceflightnewsapp.ui.utils

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LogoutWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    @Inject lateinit var authManager: AuthManager

    override fun doWork(): Result {
        authManager.logoutInBackground()
        return Result.success()
    }
}

fun scheduleLogoutWorker(context: Context){
    val workRequest = OneTimeWorkRequestBuilder<LogoutWorker>()
        .setInitialDelay(
            Constants.TIMER_INTERVAL_IN_MINUTES.toLong(),
            TimeUnit.MINUTES
        )
        .build()
    WorkManager.getInstance(context).enqueue(workRequest)
}