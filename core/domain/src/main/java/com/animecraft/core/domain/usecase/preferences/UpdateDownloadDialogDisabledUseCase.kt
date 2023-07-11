package com.animecraft.core.domain.usecase.preferences

import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.preferences.UpdateDownloadDialogDisabledUseCase.Params
import kotlinx.coroutines.withContext
import ua.animecraft.core.data.store.api.DialogsPreferencesApi
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class UpdateDownloadDialogDisabledUseCase @Inject constructor(
    private val dialogsPreferencesApi: DialogsPreferencesApi,
    private val dispatchersProvider: DispatchersProvider,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(dispatchersProvider.io()) {
            val disabled = params.disabled
            dialogsPreferencesApi.updateDownloadDialogDisabled(disabled)
        }
    }

    data class Params(
        val disabled: Boolean
    ) : UseCase.Params
}