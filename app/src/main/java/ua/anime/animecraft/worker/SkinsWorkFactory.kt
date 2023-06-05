package ua.anime.animecraft.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import ua.anime.animecraft.data.downloadmanager.SkinsDownloadManager
import ua.anime.animecraft.domain.repository.CategoryRepository
import ua.anime.animecraft.domain.repository.SkinsRepository

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

class SkinsWorkFactory @Inject constructor(
    private val skinsRepository: SkinsRepository,
    private val categoryRepository: CategoryRepository,
    private val skinsDownloadManager: SkinsDownloadManager
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SkinsWorkManager(
        context = appContext,
        workerParams = workerParameters,
        skinsRepository = skinsRepository,
        skinsDownloadManager = skinsDownloadManager,
        categoryRepository = categoryRepository
    )
}
