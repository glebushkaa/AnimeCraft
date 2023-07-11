package ua.animecraft.ad.impl

import android.annotation.SuppressLint
import com.animecraft.core.log.error
import com.animecraft.core.log.info
import com.animecraft.core.log.tag
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import ua.animecraft.activity.holder.CurrentActivityHolder
import ua.animecraft.ad.api.AppOpenAdApi
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

class AppOpenAdApiImpl @Inject constructor() : AppOpenAdApi {

    private companion object {
        const val NOT_LOADED = -1
        const val NO_ACTIVE_ACTIVITY = -2
    }

    private val appOpenAds = mutableMapOf<String, AppOpenAd>()

    @SuppressLint("VisibleForTests")
    override suspend fun loadAppOpenAd(adUnitId: String) =
        suspendCancellableCoroutine { cancellableContinuation ->
            appOpenAds[adUnitId]?.let {
                if (cancellableContinuation.isActive) {
                    cancellableContinuation.resume(Unit)
                }
                return@suspendCancellableCoroutine
            }
            val activity = CurrentActivityHolder.getCurrentActivity()
            if (activity == null) {
                activityIsNull(cancellableContinuation)
                return@suspendCancellableCoroutine
            }
            val loadCallback: AppOpenAd.AppOpenAdLoadCallback =
                object : AppOpenAd.AppOpenAdLoadCallback() {

                    override fun onAdLoaded(appOpenAd: AppOpenAd) {
                        super.onAdLoaded(appOpenAd)
                        info(this@AppOpenAdApiImpl.tag()) { "ad loaded" }
                        onAdLoaded(adUnitId, appOpenAd, cancellableContinuation)
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        super.onAdFailedToLoad(loadAdError)
                        onAdFailed(loadAdError, cancellableContinuation)
                    }
                }
            val request = AdRequest.Builder().build()
            AppOpenAd.load(activity, adUnitId, request, loadCallback)
        }

    override suspend fun showAppOpenAd(adUnitId: String) =
        suspendCancellableCoroutine { cancellableContinuation ->
            val appOpenAd = appOpenAds[adUnitId]
            if (appOpenAd == null) {
                adIsNotLoaded(cancellableContinuation)
                return@suspendCancellableCoroutine
            }
            val activity = CurrentActivityHolder.getCurrentActivity()
            if (activity == null) {
                activityIsNull(cancellableContinuation)
                return@suspendCancellableCoroutine
            }
            appOpenAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    dismissInterstitialAd(adUnitId)
                    cancellableContinuation.resume(Unit)
                    info(this@AppOpenAdApiImpl.tag()) { "ad dismissed" }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    dismissInterstitialAd(adUnitId)
                    cancellableContinuation.resume(Unit)
                    onAdFailed(adError, cancellableContinuation)
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    info(this@AppOpenAdApiImpl.tag()) { "ad showed" }
                }
            }
            appOpenAd.show(activity)
        }

    private fun adIsNotLoaded(cancellableContinuation: CancellableContinuation<Unit>) {
        try {
            throw ua.animecraft.ad.api.AdException("The ad is not loaded", "", NOT_LOADED)
        } catch (adException: ua.animecraft.ad.api.AdException) {
            error(this@AppOpenAdApiImpl.tag(), adException)
            if (cancellableContinuation.isActive) {
                cancellableContinuation.resumeWithException(adException)
            }
        }
    }

    private fun dismissInterstitialAd(adUnitId: String) {
        appOpenAds[adUnitId]?.fullScreenContentCallback = null
        appOpenAds.remove(adUnitId)
    }

    private fun onAdLoaded(
        adUnitId: String,
        ad: AppOpenAd,
        cancellableContinuation: CancellableContinuation<Unit>
    ) {
        appOpenAds[adUnitId] = ad
        if (cancellableContinuation.isActive) {
            cancellableContinuation.resume(Unit)
        }
    }

    private fun onAdFailed(
        loadError: AdError,
        cancellableContinuation: CancellableContinuation<Unit>
    ) {
        try {
            throw ua.animecraft.ad.api.AdException(loadError.message, loadError.domain, NOT_LOADED)
        } catch (adException: ua.animecraft.ad.api.AdException) {
            com.animecraft.core.log.error(this@AppOpenAdApiImpl.tag(), adException)
            if (cancellableContinuation.isActive) {
                cancellableContinuation.resume(Unit)
            }
        }
    }

    private fun activityIsNull(cancellableContinuation: CancellableContinuation<Unit>) {
        try {
            throw ua.animecraft.ad.api.AdException(
                "ad cannot be loaded when there is no active activity",
                "",
                NO_ACTIVE_ACTIVITY
            )
        } catch (adException: ua.animecraft.ad.api.AdException) {
            com.animecraft.core.log.error(this@AppOpenAdApiImpl.tag(), adException)
            if (cancellableContinuation.isActive) {
                cancellableContinuation.resumeWithException(adException)
            }
        }
    }
}
