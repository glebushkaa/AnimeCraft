package com.animecraft.core.data.store.api

import kotlinx.coroutines.flow.Flow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

interface DialogsPreferencesApi {

    val downloadDialogDisabledFlow: Flow<Boolean>

    val rateDialogDisabledFlow: Flow<Boolean>

    val rateCompletedFlow: Flow<Boolean>

    suspend fun updateDownloadDialogDisabled(isDisabled: Boolean)

    suspend fun updateRateDialogDisabled(isDisabled: Boolean)

    suspend fun updateRateCompleted(isCompleted: Boolean)
}
