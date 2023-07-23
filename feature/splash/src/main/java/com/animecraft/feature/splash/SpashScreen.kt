@file:Suppress("FunctionName")

package com.animecraft.feature.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.components.RoundedProgressIndicator
import com.anime.animecraft.core.theme.theme.AnimeCraftTheme
import com.anime.animecraft.core.theme.theme.AppTheme
import ua.anime.animecraft.feature.splash.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val state by splashViewModel.screenState.collectAsStateWithLifecycle(
        SplashScreenState()
    )

    val adAppResId = ua.anime.animecraft.core.common.android.R.string.ad_app_id
    val appNameResId = ua.anime.animecraft.core.common.android.R.string.app_name
    val openAdUnitId = stringResource(id = adAppResId)
    val appName = stringResource(id = appNameResId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .animateContentSize(),
            text = state.logoText.uppercase(),
            style = AppTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            AnimatedVisibility(
                visible = state.progressVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                RoundedProgressIndicator(
                    modifier = Modifier.size(
                        dimensionResource(id = R.dimen.splash_progress_bar_size)
                    ),
                    color = AppTheme.colors.primary,
                    strokeWidth = AppTheme.strokes.huge
                )
            }
            Spacer(
                modifier = Modifier.height(
                    AppTheme.offsets.ultraGigantic + AppTheme.offsets.ultraGigantic
                )
            )
        }
    }

    LaunchedEffect(key1 = state.finished) {
        if (state.finished) onFinish()
    }

    LaunchedEffect(key1 = Unit) {
        splashViewModel.startSplashScreen(
            adUnitId = openAdUnitId,
            appName = appName
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    AnimeCraftTheme(darkTheme = true) {
        SplashScreen(onFinish = {})
    }
}
