package com.animecraft.feature.splash

import com.anime.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.animecraft.common.SIX_HUNDRED_MILLIS
import com.animecraft.core.domain.usecase.ad.ShowOpenAdUseCase
import com.animecraft.core.domain.usecase.preferences.analytics.IncreaseTimesAppOpenedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val showOpenAdUseCase: ShowOpenAdUseCase,
    private val increaseTimesAppOpenedUseCase: IncreaseTimesAppOpenedUseCase
) : AnimeCraftViewModel() {

    private val _screenState = MutableStateFlow(SplashScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        increaseTimesAppOpened()
    }

    fun startSplashScreen(
        appName: String,
        adUnitId: String
    ) = viewModelScope.launch {
        delay(START_SPLASH_DELAY)
        startAppNameAnimation(appName)
        changeProgressState(true)
        showAppOpenAd(adUnitId)
        delay(SIX_HUNDRED_MILLIS)
        changeProgressState(false)
        delay(SIX_HUNDRED_MILLIS)
        finishSplashScreen()
    }

    private fun increaseTimesAppOpened() = viewModelScope.launch {
        increaseTimesAppOpenedUseCase()
    }

    private fun startAppNameAnimation(name: String) = viewModelScope.launch {
        var animatedName = ""
        name.forEach { letter ->
            animatedName += letter
            val state = screenState.value.copy(logoText = animatedName)
            _screenState.emit(state)
            delay(SPLASH_TEXT_CHANGE_DELAY)
        }
    }

    private suspend fun showAppOpenAd(adUnitId: String) {
        val params = ShowOpenAdUseCase.Params(adUnitId)
        showOpenAdUseCase(params)
    }

    private fun finishSplashScreen() = viewModelScope.launch {
        val state = screenState.value.copy(finished = true)
        _screenState.emit(state)
    }

    private fun changeProgressState(
        visible: Boolean
    ) = viewModelScope.launch {
        val state = screenState.value.copy(progressVisible = visible)
        _screenState.emit(state)
    }

    private companion object {
        private const val START_SPLASH_DELAY = 600L
        private const val SPLASH_TEXT_CHANGE_DELAY = 100L
    }
}
