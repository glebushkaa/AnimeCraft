package ua.animecraft.core.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

/**
 * Contract for information needed on every AnimeCraft navigation destination
 */

interface AnimeCraftDestination {
    val route: String
}

/**
 * AnimeCraft app navigation destinations
 */

object Splash : AnimeCraftDestination {
    override val route = SPLASH
}

object Main : AnimeCraftDestination {
    override val route = MAIN
}

object Favorites : AnimeCraftDestination {
    override val route = FAVORITES
}

object Settings : AnimeCraftDestination {
    override val route = SETTINGS
}

object LanguageSettings : AnimeCraftDestination {
    override val route = LANGUAGE_SETTINGS
}

object ReportSettings : AnimeCraftDestination {
    override val route = REPORT_SETTINGS
}

object DownloadSkinDialog : AnimeCraftDestination {
    override val route = DOWNLOAD_SKIN_DIALOG
}

object RatingDialog : AnimeCraftDestination {
    override val route = RATING_DIALOG
}

object ThanksDialog : AnimeCraftDestination {
    override val route = THANKS_DIALOG
}

object Info : AnimeCraftDestination {
    override val route = INFO
    const val idArg = "idArg"
    val routeWithArgs = "$route/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType }
    )
}
