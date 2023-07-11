package com.animecraft.core.domain.usecase.files

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.files.SkinFilesApi
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase.Params
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class SaveSkinGameUseCase @Inject constructor(
    private val skinFilesApi: SkinFilesApi,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
            if (params.ableToSaveInGame) {
                val result = skinFilesApi.saveSkinToMinecraft(params.fileName)
                if (!result.isSuccess) skinFilesApi.saveSkinToGallery(params.fileName) else result
            } else {
                skinFilesApi.saveSkinToGallery(params.fileName)
            }.getOrThrow()
        }
    }

    data class Params(
        val fileName: String,
        val ableToSaveInGame: Boolean
    ) : UseCase.Params
}