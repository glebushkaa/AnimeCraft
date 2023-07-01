@file:Suppress("FunctionName", "LongMethod")
@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import ua.anime.animecraft.ui.navigation.routes.favoriteScreenComposable
import ua.anime.animecraft.ui.navigation.routes.infoScreenComposable
import ua.anime.animecraft.ui.navigation.routes.languageScreenComposable
import ua.anime.animecraft.ui.navigation.routes.mainScreenComposable
import ua.anime.animecraft.ui.navigation.routes.reportScreenComposable
import ua.anime.animecraft.ui.navigation.routes.settingsScreenComposable
import ua.anime.animecraft.ui.navigation.routes.splashScreenComposable
import ua.anime.animecraft.ui.screens.favorites.FavoritesScreen
import ua.anime.animecraft.ui.screens.info.InfoScreen
import ua.anime.animecraft.ui.screens.main.MainScreen
import ua.anime.animecraft.ui.screens.settings.LanguageScreen
import ua.anime.animecraft.ui.screens.settings.ReportScreen
import ua.anime.animecraft.ui.screens.settings.SettingsScreen
import ua.anime.animecraft.ui.screens.splash.SplashScreen
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Composable
fun AnimeCraftHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Splash.route,
        modifier = modifier.background(AppTheme.colors.background)
    ) {
        splashScreenComposable {
            SplashScreen(
                onFinish = {
                    navController.navigatePopUpInclusive(
                        route = Main.route,
                        popUpRoute = Splash.route
                    )
                }
            )
        }
        mainScreenComposable {
            MainScreen(
                settingsClicked = {
                    navController.navigateSingleTopTo(Settings.route)
                },
                likeClicked = {
                    navController.navigateSingleTopTo(Favorites.route)
                },
                itemClicked = { id ->
                    navController.navigateSingleTopTo("${Info.route}/$id")
                }
            )
        }
        settingsScreenComposable {
            SettingsScreen(
                backClicked = navController::popBackStack,
                onLanguageScreenNavigate = {
                    navController.navigateSingleTopTo(LanguageSettings.route)
                },
                onReportScreenNavigate = {
                    navController.navigateSingleTopTo(ReportSettings.route)
                },
                onFavoritesScreenNavigate = {
                    navController.navigatePopUpInclusive(
                        route = Favorites.route,
                        popUpRoute = Settings.route
                    )
                }
            )
        }
        languageScreenComposable {
            LanguageScreen(
                onBackClicked = navController::popBackStack,
                onLikeNavigate = {
                    navController.popBackStack()
                    navController.navigatePopUpInclusive(
                        route = Favorites.route,
                        popUpRoute = Settings.route
                    )
                }
            )
        }
        reportScreenComposable {
            ReportScreen(
                onBackClicked = navController::popBackStack,
                onLikeNavigate = {
                    navController.popBackStack()
                    navController.navigatePopUpInclusive(
                        route = Favorites.route,
                        popUpRoute = Settings.route
                    )
                }
            )
        }
        favoriteScreenComposable {
            FavoritesScreen(
                onBackClick = navController::popBackStack,
                onItemClicked = { id ->
                    navController.navigateSingleTopTo("${Info.route}/$id")
                },
                onSettingsScreenNavigate = {
                    navController.navigatePopUpInclusive(
                        route = Settings.route,
                        popUpRoute = Favorites.route
                    )
                }
            )
        }
        infoScreenComposable { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Info.idArg) ?: 0
            InfoScreen(
                id = id,
                backClicked = navController::popBackStack
            )
        }
    }
}

fun NavController.navigatePopUpInclusive(
    route: String,
    popUpRoute: String
) = this.navigate(route) {
    popUpTo(popUpRoute) {
        inclusive = true
        saveState = true
    }
}

fun NavController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
