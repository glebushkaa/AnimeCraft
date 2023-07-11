@file:Suppress("FunctionName", "LongMethod")
@file:OptIn(ExperimentalAnimationApi::class)

package ua.animecraft.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import downloadSkinDialogComposable
import ua.anime.animecraft.ui.navigation.routes.favoriteScreenComposable
import ua.anime.animecraft.ui.screens.settings.ReportScreen
import ua.animecraft.core.navigation.composables.infoScreenComposable
import ua.animecraft.core.navigation.composables.languageScreenComposable
import ua.animecraft.core.navigation.composables.mainScreenComposable
import ua.animecraft.core.navigation.composables.ratingDialogComposable
import ua.animecraft.core.navigation.composables.reportScreenComposable
import ua.animecraft.core.navigation.composables.settingsScreenComposable
import ua.animecraft.core.navigation.composables.splashScreenComposable
import ua.animecraft.core.navigation.composables.thanksDialogComposable
import ua.animecraft.core.theme.theme.AppTheme
import ua.animecraft.feature.download.skin.DownloadSkinDialog
import ua.animecraft.feature.favorites.FavoritesScreen
import ua.animecraft.feature.info.InfoScreen
import ua.animecraft.feature.language.LanguageScreen
import ua.animecraft.feature.main.MainScreen
import ua.animecraft.feature.rating.RatingDialog
import ua.animecraft.feature.rating.ThanksDialog
import ua.animecraft.feature.settings.SettingsScreen
import ua.animecraft.feature.splash.SplashScreen

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
                onSettingsNavigate = {
                    navController.navigateSingleTopTo(Settings.route)
                },
                onFavoritesNavigate = {
                    navController.navigateSingleTopTo(Favorites.route)
                },
                onItemNavigate = { id ->
                    navController.navigateSingleTopTo("${Info.route}/$id")
                },
                onDownloadDialogNavigate = {
                    navController.navigateSingleTopTo(DownloadSkinDialog.route)
                },
                onRateDialogNavigate = {
                    navController.navigateSingleTopTo(RatingDialog.route)
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

        downloadSkinDialogComposable {
            DownloadSkinDialog(
                dismissRequest = navController::popBackStack
            )
        }

        ratingDialogComposable {
            RatingDialog(
                onDismissRequest = navController::popBackStack
            )
        }

        thanksDialogComposable {
            ThanksDialog(
                onDismissRequest = navController::popBackStack
            )
        }
    }
}
