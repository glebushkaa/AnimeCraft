@file:Suppress("FunctionName")

package com.anime.animecraft.core.components.ad

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.anime.animecraft.core.components.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

@Composable
fun BannerAd(modifier: Modifier = Modifier) {
    val currentWidth = LocalConfiguration.current.screenWidthDp
    val bannerUnitId = stringResource(id = R.string.banner_ad_id)
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        currentWidth
                    )
                )
                adUnitId = bannerUnitId
                val request = AdRequest.Builder().build()
                loadAd(request)
            }
        }
    )
}
