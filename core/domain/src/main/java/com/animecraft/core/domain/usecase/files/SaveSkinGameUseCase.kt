package com.animecraft.core.domain.usecase.files

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.repository.FilesRepository
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.files.SaveSkinGameUseCase.Params
import javax.inject.Inject
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class SaveSkinGameUseCase @Inject constructor(
    private val filesRepository: FilesRepository,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
            val fileName = params.fileName.ifBlank {
                filesRepository.saveSkinFromStorage(params.id)
            }
            filesRepository.saveGameSkinToUser(fileName).getOrThrow()
        }
    }

    data class Params(
        val id: Int,
        val fileName: String
    ) : UseCase.Params
}
