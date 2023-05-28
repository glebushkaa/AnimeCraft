package ua.anime.animecraft.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import ua.anime.animecraft.FAVORITES
import ua.anime.animecraft.INFO
import ua.anime.animecraft.MAIN
import ua.anime.animecraft.SETTINGS
import ua.anime.animecraft.SPLASH

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

object Info : AnimeCraftDestination {
    override val route = INFO
    const val idArg = "idArg"
    val routeWithArgs = "$route/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType }
    )
}
