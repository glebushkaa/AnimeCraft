@file:Suppress("FunctionName", "FunctionOnlyReturningConstant")
@file:OptIn(ExperimentalAnimationApi::class)

package com.animecraft.core.navigation.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.animecraft.animecraft.common.HALF_SECOND
import com.animecraft.core.navigation.Info
import com.google.accompanist.navigation.animation.composable

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023
 */

private val animationSpec = tween<IntOffset>(
    durationMillis = HALF_SECOND.toInt()
)

fun NavGraphBuilder.infoScreenComposable(
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = Info.routeWithArgs,
        content = content,
        arguments = Info.arguments,
        enterTransition = { infoEnterAnimation() },
        exitTransition = { infoExitAnimation() },
        popEnterTransition = { infoPopEnterAnimation() },
        popExitTransition = { infoPopExitAnimation() }
    )
}

private fun AnimatedContentScope<NavBackStackEntry>.infoEnterAnimation(): EnterTransition {
    return slideInVertically(animationSpec) { it }
}

private fun AnimatedContentScope<NavBackStackEntry>.infoExitAnimation(): ExitTransition {
    return slideOutVertically(animationSpec) { it }
}

private fun AnimatedContentScope<NavBackStackEntry>.infoPopEnterAnimation(): EnterTransition? = null

private fun AnimatedContentScope<NavBackStackEntry>.infoPopExitAnimation(): ExitTransition {
    return slideOutVertically(animationSpec) { it }
}
