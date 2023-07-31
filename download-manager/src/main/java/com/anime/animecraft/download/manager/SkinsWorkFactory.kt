package com.anime.animecraft.download.manager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.animecraft.core.domain.repository.CategoryRepository
import com.animecraft.core.domain.repository.SkinsRepository
import javax.inject.Inject

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
