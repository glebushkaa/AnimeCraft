@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.screens.splash

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.anime.animecraft.R
import ua.anime.animecraft.core.common.SIX_HUNDRED_MILLIS
import ua.anime.animecraft.ui.common.RoundedProgressIndicator
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 4/30/2023.
 */

@Composable
fun SplashScreen(
    onFinish: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val openAdUnitId = stringResource(id = R.string.ad_app_id)
    val string = stringResource(id = R.string.app_name)
    var logoText by rememberSaveable { mutableStateOf("") }
    var isProgressBarShown by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .animateContentSize(),
            text = logoText.uppercase(),
            style = AppTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            AnimatedVisibility(
                visible = isProgressBarShown,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                RoundedProgressIndicator(
                    modifier = Modifier
                        .size(AppTheme.sizes.screens.splash.progressBarSize),
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

    LaunchedEffect(key1 = Unit) {
        delay(START_SPLASH_DELAY)
        string.forEach { c ->
            logoText += c
            delay(SPLASH_TEXT_CHANGE_DELAY)
        }
        isProgressBarShown = true
        splashViewModel.showAppOpenAd(
            adUnitId = openAdUnitId,
            onFinish = {
                scope.launch {
                    delay(SIX_HUNDRED_MILLIS)
                    isProgressBarShown = false
                    delay(SIX_HUNDRED_MILLIS)
                    onFinish()
                }
            }
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

private const val START_SPLASH_DELAY = 600L
private const val SPLASH_TEXT_CHANGE_DELAY = 100L
