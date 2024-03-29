@file:OptIn(ExperimentalAnimationApi::class)

package com.animecraft.core.navigation.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.animecraft.animecraft.common.HALF_SECOND
import com.animecraft.core.navigation.Splash
import com.google.accompanist.navigation.animation.composable

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023
 */

private val animationSpec = tween<Float>(HALF_SECOND.toInt())

fun NavGraphBuilder.splashScreenComposable(
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = Splash.route,
        content = content,
        enterTransition = { splashEnterAnimation() },
        exitTransition = { splashExitAnimation() },
        popEnterTransition = { splashPopEnterAnimation() },
        popExitTransition = { splashPopExitAnimation() }
    )
}

private fun AnimatedContentScope<NavBackStackEntry>.splashEnterAnimation(): EnterTransition {
    return fadeIn(animationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.splashExitAnimation(): ExitTransition {
    return fadeOut(animationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.splashPopEnterAnimation(): EnterTransition {
    return fadeIn(animationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.splashPopExitAnimation(): ExitTransition {
    return fadeOut(animationSpec)
}
