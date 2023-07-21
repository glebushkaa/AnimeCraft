package com.animecraft.core.domain.usecase.preferences.analytics

import com.animecraft.core.data.store.api.SettingsPreferencesApi
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultNoneParamsUseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/17/2023
 */

class IncreaseTimesAppOpenedUseCase @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val settingsPreferencesApi: SettingsPreferencesApi,
    useCaseLogger: UseCaseLogger
) : ResultNoneParamsUseCase<Unit>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        withContext(dispatchersProvider.io()) {
            val timesAppOpened = settingsPreferencesApi.timesAppOpened.first() ?: 0
            settingsPreferencesApi.updateTimesAppOpened(timesAppOpened + 1)
        }
    }
}
