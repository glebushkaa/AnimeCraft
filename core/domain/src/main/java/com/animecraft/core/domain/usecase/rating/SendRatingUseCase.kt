package com.animecraft.core.domain.usecase.rating

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.rating.SendRatingUseCase.Params
import com.animecraft.core.network.api.NetworkDatabaseApi
import javax.inject.Inject
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/21/2023
 */

class SendRatingUseCase @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val networkDatabaseApi: NetworkDatabaseApi,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
            networkDatabaseApi.sendRating(params.rating)
        }
    }

    data class Params(
        val rating: Int
    ) : UseCase.Params
}
