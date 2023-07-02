package com.animecraft.core.domain.usecase.prefs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.anime.animecraft.domain.prefs.DialogsPreferencesApi
import com.animecraft.core.domain.usecase.core.ResultNoneParamsUseCase
import com.animecraft.core.domain.usecase.core.UseCaseLogger
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class CheckDownloadDialogDisabledUseCase @Inject constructor(
    private val dialogsPreferencesApi: DialogsPreferencesApi,
    useCaseLogger: UseCaseLogger
): ResultNoneParamsUseCase<Boolean>(useCaseLogger) {

    override suspend fun invoke() = runCatching {
        withContext(Dispatchers.IO) {
            dialogsPreferencesApi.isDownloadDialogDisabled()
        }
    }
}