@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.anime.animecraft.ui.screens.favorites.FavoritesScreen
import ua.anime.animecraft.ui.screens.info.InfoScreen
import ua.anime.animecraft.ui.screens.main.MainScreen
import ua.anime.animecraft.ui.screens.settings.SettingsScreen
import ua.anime.animecraft.ui.screens.splash.SplashScreen

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Composable
fun AnimeCraftHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Splash.route, modifier = modifier) {
        composable(route = Splash.route) {
            SplashScreen {
                navController.navigate(route = Main.route) {
                    popUpTo(Splash.route) {
                        inclusive = true
                        saveState = true
                    }
                }
            }
        }
        composable(route = Main.route) {
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
        composable(route = Settings.route) {
            SettingsScreen {
                navController.popBackStack()
            }
        }
        composable(route = Favorites.route) {
            FavoritesScreen(
                backClicked = {
                    navController.popBackStack()
                },
                itemClicked = { id ->
                    navController.navigateSingleTopTo("${Info.route}/$id")
                }
            )
        }
        composable(
            route = Info.routeWithArgs,
            arguments = Info.arguments
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Info.idArg) ?: 0
            InfoScreen(
                id = id,
                backClicked = {
                    navController.popBackStack()
                }
            )
        }
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
