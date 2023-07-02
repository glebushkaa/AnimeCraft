package com.animecraft.core.domain.usecase.skin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.animecraft.core.domain.repository.FavoritesRepository
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase.Params
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class UpdateSkinFavoriteStateUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            favoritesRepository.updateSkinFavoriteState(
                id = params.skinId,
                favorite = params.favorite
            )
        }
    }

    data class Params(
        val skinId: Int,
        val favorite: Boolean
    ) : UseCase.Params
}