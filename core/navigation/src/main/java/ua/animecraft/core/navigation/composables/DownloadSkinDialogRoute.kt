import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import ua.animecraft.core.navigation.DownloadSkinDialog

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

fun NavGraphBuilder.downloadSkinDialogComposable(
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(
        route = DownloadSkinDialog.route,
        content = content
    )
}
