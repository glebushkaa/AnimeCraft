package ua.animecraft.core.theme.theme.sizes.screens

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

data class ScreenSizes(
    val main: MainScreenSizes = MainScreenSizes(),
    val info: InfoScreenSizes = InfoScreenSizes(),
    val settings: SettingsScreenSizes = SettingsScreenSizes(),
    val splash: SplashScreenSizes = SplashScreenSizes(),
    val favorites: FavoritesScreenSizes = FavoritesScreenSizes(),
    val language: LanguageScreenSizes = LanguageScreenSizes(),
    val report: ReportScreenSizes = ReportScreenSizes()
)
