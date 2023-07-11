package ua.animecraft.feature.splash

import com.animecraft.core.android.AnimeCraftViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import ua.animecraft.ad.api.AppOpenAdApi

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appOpenAdApi: ua.animecraft.ad.api.AppOpenAdApi
) : AnimeCraftViewModel() {

    private var isAdShowed = false

    fun showAppOpenAd(adUnitId: String, onFinish: () -> Unit = {}) = viewModelScope.launch {
        runCatching {
            if (isAdShowed) return@launch
            isAdShowed = true
            appOpenAdApi.loadAppOpenAd(adUnitId)
            appOpenAdApi.showAppOpenAd(adUnitId)
            onFinish()
        }.onFailure {
            onFinish()
        }
    }
}
