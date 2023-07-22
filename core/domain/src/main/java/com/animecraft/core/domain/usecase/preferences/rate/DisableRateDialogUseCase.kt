package com.animecraft.core.domain.usecase.preferences.rate

import com.animecraft.core.data.store.api.DialogsPreferencesApi
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultNoneParamsUseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import javax.inject.Inject
import kotlinx.coroutines.withContext

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/21/2023
 */

class DisableRateDialogUseCase @Inject constructor(
    private val dialogsPreferencesApi: DialogsPreferencesApi,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultNoneParamsUseCase<Unit>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        withContext(dispatchersProvider.io()) {
            dialogsPreferencesApi.updateRateDialogDisabled(true)
        }
    }
}
