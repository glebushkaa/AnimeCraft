@file:Suppress("FunctionOnlyReturningConstant")
@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui.navigation.routes

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import ua.anime.animecraft.core.common.HALF_SECOND
import ua.anime.animecraft.ui.navigation.Main
import ua.anime.animecraft.ui.navigation.Settings

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023
 */

private val animationSpec = tween<IntOffset>(HALF_SECOND.toInt())

fun NavGraphBuilder.settingsScreenComposable(
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = Settings.route,
        content = content,
        enterTransition = { settingsEnterAnimation() },
        exitTransition = { settingsExitAnimation() },
        popEnterTransition = { settingsPopEnterAnimation() },
        popExitTransition = { settingsPopExitAnimation() }
    )
}

private fun AnimatedContentScope<NavBackStackEntry>.settingsEnterAnimation(): EnterTransition? {
    return when (initialState.destination.route) {
        Main.route -> slideInHorizontally(animationSpec) { it }
        else -> null
    }
}

private fun AnimatedContentScope<NavBackStackEntry>.settingsExitAnimation(): ExitTransition? {
    return when (targetState.destination.route) {
        Main.route -> slideOutHorizontally(animationSpec) { it }
        else -> null
    }
}

private fun AnimatedContentScope<NavBackStackEntry>.settingsPopEnterAnimation(): EnterTransition? =
    null

private fun AnimatedContentScope<NavBackStackEntry>.settingsPopExitAnimation(): ExitTransition {
    return slideOutHorizontally(animationSpec) { it }
}
