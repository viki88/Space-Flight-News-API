package com.vikination.spaceflightnewsapp.ui.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.auth0.android.authentication.storage.CredentialsManager
import com.vikination.spaceflightnewsapp.R
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

@HiltWorker
class LogoutWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    private val authRepository: AuthRepository,
) : Worker(context, params) {

    override fun doWork(): Result {
        return runBlocking {
            authRepository.logoutInBackground(
                context.resources.getString(R.string.com_auth0_client_id)
            )
                .catch { e -> Log.i("TAG", "doWork error: ${e.message}") }
                .collect {
                    isSuccess ->
                    Log.i("TAG", "doWork: $isSuccess")
                    if (isSuccess) NotificationHelper(context).showSessionExpiredNotification()
                }
            Result.success()
        }
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