package com.animecraft.feature.settings

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.anime.animecraft.core.android.Event
import com.animecraft.core.domain.usecase.preferences.GetDarkModeStateFlowUseCase
import com.animecraft.core.domain.usecase.preferences.GetDownloadDialogDisabledFlowUseCase
import com.animecraft.core.domain.usecase.preferences.UpdateDarkModeUseCase
import com.animecraft.core.domain.usecase.preferences.UpdateDownloadDialogDisabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getDownloadDialogDisabledFlowUseCase: GetDownloadDialogDisabledFlowUseCase,
    private val updateDownloadDialogDisabledUseCase: UpdateDownloadDialogDisabledUseCase,
    private val updateDarkModeUseCase: UpdateDarkModeUseCase,
    private val getDarkModeStateFlowUseCase: GetDarkModeStateFlowUseCase
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(SettingsScreenState())
    val screenState = _screenState.asStateFlow()

    private var systemInDarkModeByDefault = false

    init {
        collectDownloadDialogDisabledFlow()
        collectDarkModeState()
    }

    fun updateDarkModeState() = viewModelScope.launch {
        val darkModeEnabled = _screenState.value.darkModeEnabled
        val params = UpdateDarkModeUseCase.Params(darkModeEnabled)
        updateDarkModeUseCase(params)
    }

    fun saveSystemDarkMode(
        isSystemInDarkModeByDefault: Boolean
    ) = viewModelScope.launch {
        systemInDarkModeByDefault = isSystemInDarkModeByDefault
        collectDarkModeState()
    }

    fun showRatingDialog() = viewModelScope.launch {
        val state = _screenState.value.copy(
            ratingDialogShown = Event(true)
        )
        _screenState.emit(state)
    }

    fun updateDownloadDialogSetting() = viewModelScope.launch {
        val downloadDialogDisabled = _screenState.value.downloadDialogDisabled
        val params = UpdateDownloadDialogDisabledUseCase.Params(!downloadDialogDisabled)
        updateDownloadDialogDisabledUseCase(params)
    }

    private fun collectDownloadDialogDisabledFlow() = viewModelScope.launch {
        getDownloadDialogDisabledFlowUseCase().getOrNull()?.collect {
            val state = _screenState.value.copy(
                downloadDialogDisabled = it
            )
            _screenState.emit(state)
        }
    }

    private fun collectDarkModeState() = viewModelScope.launch {
        getDarkModeStateFlowUseCase().getOrNull()?.collect {
            val darkModeEnabled = it ?: systemInDarkModeByDefault
            val state = _screenState.value.copy(
                darkModeEnabled = darkModeEnabled
            )
            _screenState.emit(state)
        }
    }
}
