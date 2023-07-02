package com.animecraft.core.domain.usecase.prefs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.anime.animecraft.domain.prefs.DialogsPreferencesApi
import com.animecraft.core.domain.usecase.core.ResultUseCase
import com.animecraft.core.domain.usecase.core.UseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import com.animecraft.core.domain.usecase.prefs.ChangeDisableDownloadDialogPrefUseCase.Params
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class ChangeDisableDownloadDialogPrefUseCase @Inject constructor(
    private val dialogsPreferencesApi: DialogsPreferencesApi,
    useCaseLogger: UseCaseLogger
) : ResultUseCase<Unit, Params>(useCaseLogger) {

    override suspend fun invoke(params: Params) = runCatching {
        withContext(Dispatchers.IO) {
            dialogsPreferencesApi.setDownloadDialogDisabled(params.value)
        }
    }

    data class Params(
        val value: Boolean
    ) : UseCase.Params
}