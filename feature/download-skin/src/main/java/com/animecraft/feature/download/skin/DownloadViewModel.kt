package com.animecraft.feature.download.skin

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.domain.usecase.preferences.download.UpdateDownloadDialogDisabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val updateDownloadDialogDisabledUseCase: UpdateDownloadDialogDisabledUseCase
) : AnimeCraftViewModel() {

    fun updateDownloadDialogDisabled() = viewModelScope.launch {
        val params = UpdateDownloadDialogDisabledUseCase.Params(true)
        updateDownloadDialogDisabledUseCase(params)
    }
}
