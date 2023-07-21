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
import com.animecraft.animecraft.common.ONE_SECOND
import com.animecraft.animecraft.common.THREE_HUNDRED_MILLIS
import com.animecraft.core.navigation.Main
import com.animecraft.core.navigation.Splash
import com.google.accompanist.navigation.animation.composable

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023
 */

private val exitAnimationSpec = tween<Float>(durationMillis = HALF_SECOND.toInt())
private val enterSplashAnimationSpec = tween<Float>(durationMillis = ONE_SECOND.toInt())
private val enterAnimationSpec = tween<Float>(durationMillis = THREE_HUNDRED_MILLIS.toInt())

fun NavGraphBuilder.mainScreenComposable(
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = Main.route,
        content = content,
        enterTransition = { mainEnterAnimation() },
        exitTransition = { mainExitAnimation() },
        popEnterTransition = { mainPopEnterAnimation() },
        popExitTransition = { mainPopExitAnimation() }
    )
}

private fun AnimatedContentScope<NavBackStackEntry>.mainEnterAnimation(): EnterTransition {
    return createEnterAnimation()
}

private fun AnimatedContentScope<NavBackStackEntry>.mainExitAnimation(): ExitTransition {
    return fadeOut(exitAnimationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.mainPopEnterAnimation(): EnterTransition {
    return createEnterAnimation()
}

private fun AnimatedContentScope<NavBackStackEntry>.mainPopExitAnimation(): ExitTransition {
    return fadeOut(exitAnimationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.createEnterAnimation(): EnterTransition {
    val animationSpec = if (initialState.destination.route == Splash.route) {
        enterSplashAnimationSpec
    } else {
        enterAnimationSpec
    }
    return fadeIn(animationSpec)
}
