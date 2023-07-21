package com.animecraft.core.domain.usecase.skin

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.repository.SkinsRepository
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.skin.GetSkinFlowUseCase.Params
import com.animecraft.model.Skin
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

class GetSkinFlowUseCase @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val skinsRepository: SkinsRepository,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Flow<Skin>, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
            skinsRepository.getSkinFlow(params.id)
        }
    }

    data class Params(
        val id: Int
    ) : UseCase.Params
}
