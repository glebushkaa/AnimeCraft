package ua.anime.animecraft.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ua.anime.animecraft.domain.repository.SkinsRepository
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

class SkinsWorkFactory @Inject constructor(
    private val skinsRepository: SkinsRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SkinsWorkManager(
        context = appContext,
        workerParams = workerParameters,
        skinsRepository = skinsRepository
    )
}
