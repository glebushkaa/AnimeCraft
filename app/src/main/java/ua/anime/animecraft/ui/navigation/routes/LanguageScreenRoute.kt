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
import ua.anime.animecraft.ui.navigation.LanguageSettings

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/2/2023
 */

private val animationSpec = tween<IntOffset>(HALF_SECOND.toInt())

fun NavGraphBuilder.languageScreenComposable(
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = LanguageSettings.route,
        content = content,
        enterTransition = { languageEnterAnimation() },
        exitTransition = { languageExitAnimation() },
        popEnterTransition = { languagePopEnterAnimation() },
        popExitTransition = { languagePopExitAnimation() }
    )
}

private fun AnimatedContentScope<NavBackStackEntry>.languageEnterAnimation(): EnterTransition {
    return slideInHorizontally(animationSpec) { it }
}
private fun AnimatedContentScope<NavBackStackEntry>.languageExitAnimation(): ExitTransition {
    return slideOutHorizontally(animationSpec) { it }
}

private fun AnimatedContentScope<NavBackStackEntry>.languagePopEnterAnimation(): EnterTransition {
    return slideInHorizontally(animationSpec) { it }
}

private fun AnimatedContentScope<NavBackStackEntry>.languagePopExitAnimation(): ExitTransition {
    return slideOutHorizontally(animationSpec) { it }
}
