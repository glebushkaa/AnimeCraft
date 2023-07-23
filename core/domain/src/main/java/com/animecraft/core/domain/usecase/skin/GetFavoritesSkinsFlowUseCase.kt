package com.animecraft.core.domain.usecase.skin

import com.animecraft.core.domain.repository.FavoritesRepository
import com.animecraft.core.domain.usecase.core.ResultNoneParamsUseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.model.Skin
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class GetFavoritesSkinsFlowUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    useCaseLogger: UseCaseLogger
) : ResultNoneParamsUseCase<Flow<List<Skin>>>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        favoritesRepository.getFavoritesSkins()
    }
}
