package com.animecraft.core.domain.usecase.ad

import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.ad.ShowOpenAdUseCase.Params
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.ad.api.AppOpenAdApi
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

class ShowOpenAdUseCase @Inject constructor(
    private val appOpenAdApi: AppOpenAdApi,
    useCaseLogger: UseCaseLogger
): ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        appOpenAdApi.run {
            loadAppOpenAd(params.adUnitId)
            showAppOpenAd(params.adUnitId)
        }
    }

    data class Params(
        val adUnitId: String
    ): UseCase.Params
}