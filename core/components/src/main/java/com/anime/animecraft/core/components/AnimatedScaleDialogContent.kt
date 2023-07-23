@file:Suppress("FunctionName")
@file:OptIn(ExperimentalAnimationApi::class)

package com.anime.animecraft.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023.
 */

const val PRE_DISMISS_DELAY = 400L

private const val ENTER_ANIMATION_DURATION = 600
private const val EXIT_FADE_ANIMATION_DURATION = 200
private const val EXIT_SCALE_ANIMATION_DURATION = 300

private val enterAnimation =
    scaleIn(tween(ENTER_ANIMATION_DURATION)) + fadeIn(tween(ENTER_ANIMATION_DURATION))
private val exitAnimation =
    scaleOut(tween(EXIT_SCALE_ANIMATION_DURATION)) + fadeOut(tween(EXIT_FADE_ANIMATION_DURATION))

@Composable
fun AnimatedScaleDialogContent(
    content: @Composable () -> Unit,
    isDismissed: Boolean
) {
    var animateTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(PRE_DISMISS_DELAY)
        animateTrigger = true
    }

    LaunchedEffect(key1 = isDismissed) {
        if (isDismissed) animateTrigger = false
    }

    AnimatedVisibility(
        visible = animateTrigger,
        enter = enterAnimation,
        exit = exitAnimation
    ) {
        content()
    }
}
