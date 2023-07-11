package ua.animecraft.download.manager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.domain.repository.SkinsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ua.animecraft.model.Skin
import java.io.File
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

@HiltWorker
class SkinsWorkManager @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val skinsRepository: SkinsRepository,
    private val categoryRepository: CategoryRepository,
    private val skinsDownloadManager: SkinsDownloadManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        runCatching {
            startForegroundService()
            val localSkins = skinsRepository.getSkins()
            val skinsImageMap = saveSkin(localSkins)
            categoryRepository.updateLocalCategoriesFromNetwork()
            skinsRepository.updateLocalSkinsFromNetwork(skinsImageMap)
            Result.success()
        }.onFailure {
            com.animecraft.core.log.error("SkinsWorkManager", it) { it.message ?: "" }
        }.getOrDefault(Result.failure())
    }

    private suspend fun saveSkin(skins: List<Skin>) = coroutineScope {
        skins.map {
            async {
                val gameFileName = getSkinsGameFileName(it.id)
                saveImageFiles(it.id, gameFileName)
                it.id to gameFileName
            }
        }.awaitAll().toMap()
    }

    private suspend fun saveImageFiles(id: Int, gameFileName: String) = runCatching {
        val gameImageUrl = skinsRepository.getSkinsGameImageUrl(id) ?: ""
        val request = skinsDownloadManager.getDownloadGameImagesByUrlRequest(
            gameImageUrl,
            gameFileName
        ) ?: return@runCatching
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)
        skinsDownloadManager.gameDownloadIdsMap[id] = downloadId
    }

    /**
     * This function is for getting game image file name.
     * If file doesn't exist we return new generated file name
     * If file exist, but is not readable then we return new generated file name as well
     * And if file exist and is readable we return old sound file name
     */

    private suspend fun getSkinsGameFileName(id: Int): String {
        val fileName = "${UUID.randomUUID()}.png"
        return skinsRepository.getSkinsGameFileName(id)?.let {
            val mediaDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File(mediaDirectory, it)
            if (file.exists() && file.canRead()) it else fileName
        } ?: fileName
    }

    private suspend fun startForegroundService() {
        setForeground(getForegroundInfo())
    }

    override suspend fun getForegroundInfo() =
        ForegroundInfo(WORKER_NOTIFICATION_ID, context.createDownloadNotification())

    companion object {
        fun startSkinsWorker(): OneTimeWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            return OneTimeWorkRequest.Builder(SkinsWorkManager::class.java)
                .addTag(WORKER_TAG)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, FIVE_MINUTES, TimeUnit.MINUTES)
                .build()
        }

        private const val WORKER_NOTIFICATION_ID = 10
        private const val FIVE_MINUTES = 5L
        private const val WORKER_TAG = "skins_worker"

        const val WORK_SKINS_CHANNEL_ID = "work_skins_channel"
    }
}
