package com.animecraft.core.domain.usecase.preferences.darkmode

import com.animecraft.core.data.store.api.SettingsPreferencesApi
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultNoneParamsUseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

class GetDarkModeStateFlowUseCase @Inject constructor(
    private val settingsPreferencesApi: SettingsPreferencesApi,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultNoneParamsUseCase<Flow<Boolean?>>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        withContext(dispatchersProvider.io()) {
            settingsPreferencesApi.darkModeEnabled
        }
    }
}
