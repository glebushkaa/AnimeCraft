package com.animecraft.core.domain.usecase.files

import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.anime.animecraft.data.files.SkinFilesApiImpl
import ua.anime.animecraft.domain.files.SkinFilesApi
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase.Params
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class SaveSkinGameUseCase @Inject constructor(
    private val skinFilesApi: SkinFilesApi,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                skinFilesApi.saveSkinToGallery(params.fileName)
            } else {
                val result = skinFilesApi.saveSkinToMinecraft(params.fileName)
                if (!result.isSuccess) skinFilesApi.saveSkinToGallery(params.fileName) else result
            }.getOrThrow()
        }
    }

    data class Params(
        val fileName: String
    ) : UseCase.Params
}