package com.vikination.spaceflightnewsapp.di.modules

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import com.vikination.spaceflightnewsapp.ui.utils.LogoutWorker
import com.vikination.spaceflightnewsapp.ui.utils.worker.ChildWorkerFactory
import javax.inject.Inject

class LogoutWorkerFactory @Inject constructor(private val authRepository: AuthRepository):
    ChildWorkerFactory {
    override fun create(appContext: Context, workerParameters: WorkerParameters): ListenableWorker {
        return LogoutWorker(appContext, workerParameters, authRepository)
    }
}
