package com.anime.animecraft

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.anime.animecraft.utils.DarkModeHandler.updateDarkModeState
import com.animecraft.core.domain.DispatchersProvider
import com.animecraft.core.domain.usecase.preferences.darkmode.GetDarkModeStateFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getDarkModeStateFlowUseCase: GetDarkModeStateFlowUseCase
) : AnimeCraftViewModel() {

    fun setupDarkMode(
        isSystemInDarkModeByDefault: Boolean
    ) = viewModelScope.launch(dispatchersProvider.io()) {
        getDarkModeStateFlowUseCase().getOrNull()?.collect {
            val darkModeEnabled = it ?: isSystemInDarkModeByDefault
            updateDarkModeState(darkModeEnabled)
        }
    }
}
