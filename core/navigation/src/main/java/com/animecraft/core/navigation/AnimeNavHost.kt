@file:Suppress("FunctionName", "LongMethod")
@file:OptIn(ExperimentalAnimationApi::class)

package com.animecraft.core.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.dialog
import com.google.accompanist.navigation.animation.AnimatedNavHost
import downloadSkinDialogComposable
import com.anime.animecraft.ui.navigation.routes.favoriteScreenComposable
import com.animecraft.feature.report.ReportScreen
import com.animecraft.core.navigation.composables.infoScreenComposable
import com.animecraft.core.navigation.composables.languageScreenComposable
import com.animecraft.core.navigation.composables.mainScreenComposable
import com.animecraft.core.navigation.composables.ratingDialogComposable
import com.animecraft.core.navigation.composables.reportScreenComposable
import com.animecraft.core.navigation.composables.settingsScreenComposable
import com.animecraft.core.navigation.composables.splashScreenComposable
import com.animecraft.core.navigation.composables.thanksDialogComposable
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.feature.download.skin.DownloadSkinDialog
import com.animecraft.feature.favorites.FavoritesScreen
import com.animecraft.feature.info.InfoScreen
import com.animecraft.feature.language.LanguageScreen
import com.animecraft.feature.main.MainScreen
import com.animecraft.feature.rating.RatingDialog
import com.animecraft.feature.rating.ThanksDialog
import com.animecraft.feature.settings.SettingsScreen
import com.animecraft.feature.splash.SplashScreen
import com.google.accompanist.navigation.animation.composable

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
        infoScreenComposable {
            InfoScreen(
                onBackClicked = navController::popBackStack
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
