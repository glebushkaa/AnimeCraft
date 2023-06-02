@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui.navigation.routes

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
import com.google.accompanist.navigation.animation.composable
import ua.anime.animecraft.core.common.HALF_SECOND
import ua.anime.animecraft.ui.navigation.Main

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023
 */

private val exitAnimationSpec = tween<Float>(durationMillis = HALF_SECOND.toInt())
private val enterAnimationSpec = tween<Float>(durationMillis = 300)

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
    return fadeIn(enterAnimationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.mainExitAnimation(): ExitTransition {
    return fadeOut(exitAnimationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.mainPopEnterAnimation(): EnterTransition {
    return fadeIn(enterAnimationSpec)
}

private fun AnimatedContentScope<NavBackStackEntry>.mainPopExitAnimation(): ExitTransition {
    return fadeOut(exitAnimationSpec)
}
