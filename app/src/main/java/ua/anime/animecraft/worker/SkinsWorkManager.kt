package ua.anime.animecraft.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import ua.anime.animecraft.domain.repository.SkinsRepository
import java.util.concurrent.TimeUnit


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

@HiltWorker
class SkinsWorkManager @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val skinsRepository: SkinsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork() = coroutineScope {
        startForegroundService()
        skinsRepository.updateLocalSkinsFromNetwork()
        Result.success()
    }

    private suspend fun startForegroundService() {
        setForeground(getForegroundInfo())
    }

    override suspend fun getForegroundInfo() =
        ForegroundInfo(WORKER_NOTIFICATION_ID, context.createDownloadNotification())

    companion object {
        fun startSkinsWorker(): OneTimeWorkRequest {
            return OneTimeWorkRequest.Builder(SkinsWorkManager::class.java)
                .addTag(WORKER_TAG)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, FIVE_MINUTES, TimeUnit.MINUTES)
                .build()
        }

        private const val WORKER_NOTIFICATION_ID = 10
        private const val FIVE_MINUTES = 5L
        private const val WORKER_TAG = "skins_worker"

        const val WORK_SKINS_CHANNEL_ID = "work_skins_channel"
    }
}