package com.animecraft.core.domain.usecase.preferences.download

import com.animecraft.core.data.store.api.DialogsPreferencesApi
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.preferences.download.UpdateDownloadDialogDisabledUseCase.Params
import javax.inject.Inject
import kotlinx.coroutines.withContext

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
