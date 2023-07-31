package com.animecraft.core.domain.usecase.preferences.language

import com.animecraft.core.data.store.api.SettingsPreferencesApi
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.preferences.language.UpdateLanguageUseCase.Params
import javax.inject.Inject
import kotlinx.coroutines.withContext

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
