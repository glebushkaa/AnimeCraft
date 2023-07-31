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
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.animecraft.animecraft.common.HALF_SECOND
import com.animecraft.core.navigation.Favorites
import com.animecraft.core.navigation.Info
import com.google.accompanist.navigation.animation.composable

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023
 */

private val slideAnimationSpec = tween<IntOffset>(HALF_SECOND.toInt())
private val fadeAnimationSpec = tween<Float>(HALF_SECOND.toInt())

fun NavGraphBuilder.favoriteScreenComposable(
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = Favorites.route,
        content = content,
        enterTransition = { favoriteEnterAnimation() },
        exitTransition = { favoriteExitAnimation() },
        popEnterTransition = { favoritePopEnterAnimation() },
        popExitTransition = { favoritePopExitAnimation() }
    )
}

private fun AnimatedContentScope<NavBackStackEntry>.favoriteEnterAnimation(): EnterTransition {
    return when (initialState.destination.route) {
        Info.routeWithArgs -> fadeIn(fadeAnimationSpec)
        else -> slideInHorizontally(slideAnimationSpec) { it }
    }
}

private fun AnimatedContentScope<NavBackStackEntry>.favoriteExitAnimation(): ExitTransition {
    return when (targetState.destination.route) {
        Info.routeWithArgs -> fadeOut(fadeAnimationSpec)
        else -> slideOutHorizontally(slideAnimationSpec) { it }
    }
}

private fun AnimatedContentScope<NavBackStackEntry>.favoritePopEnterAnimation(): EnterTransition {
    return when (initialState.destination.route) {
        Info.routeWithArgs -> fadeIn(fadeAnimationSpec)
        else -> slideInHorizontally(slideAnimationSpec) { it }
    }
}

private fun AnimatedContentScope<NavBackStackEntry>.favoritePopExitAnimation(): ExitTransition {
    return when (targetState.destination.route) {
        Info.routeWithArgs -> fadeOut(fadeAnimationSpec)
        else -> slideOutHorizontally(slideAnimationSpec) { it }
    }
}
