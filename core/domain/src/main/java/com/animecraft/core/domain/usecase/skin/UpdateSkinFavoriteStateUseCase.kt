package com.animecraft.core.domain.usecase.skin

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.repository.FavoritesRepository
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.skin.UpdateSkinFavoriteStateUseCase.Params
import javax.inject.Inject
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class UpdateSkinFavoriteStateUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
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
