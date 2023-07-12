package com.animecraft.core.domain.usecase.preferences

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.preferences.UpdateLanguageUseCase.Params
import kotlinx.coroutines.withContext
import com.animecraft.core.data.store.api.SettingsPreferencesApi
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

class UpdateLanguageUseCase @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val settingsPreferencesApi: SettingsPreferencesApi,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
            settingsPreferencesApi.updateLanguage(params.language)
        }
    }

    data class Params(
        val language: String
    ) : UseCase.Params
}