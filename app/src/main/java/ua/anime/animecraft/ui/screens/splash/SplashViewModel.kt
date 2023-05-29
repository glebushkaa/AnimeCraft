package ua.anime.animecraft.ui.screens.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.anime.animecraft.ad.api.AppOpenAdApi
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appOpenAdApi: AppOpenAdApi
) : AnimeCraftViewModel() {

    private var isAdShowed = false

    fun showAppOpenAd(adUnitId: String, onFinish: () -> Unit = {}) {
        viewModelScope.launch {
            if (isAdShowed) return@launch
            isAdShowed = true
            appOpenAdApi.loadAppOpenAd(adUnitId)
            appOpenAdApi.showAppOpenAd(adUnitId)
            onFinish()
        }
    }
}