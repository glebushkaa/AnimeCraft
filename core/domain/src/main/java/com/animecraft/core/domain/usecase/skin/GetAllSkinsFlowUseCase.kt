package com.animecraft.core.domain.usecase.skin

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.repository.SkinsRepository
import com.animecraft.core.domain.usecase.core.ResultNoneParamsUseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.model.Skin
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class GetAllSkinsFlowUseCase @Inject constructor(
    private val skinRepository: SkinsRepository,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultNoneParamsUseCase<Flow<List<Skin>>>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        withContext(dispatchersProvider.io()) {
            skinRepository.getSkinsFlow()
        }
    }
}
