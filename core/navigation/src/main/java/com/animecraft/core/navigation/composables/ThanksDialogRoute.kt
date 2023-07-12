package com.animecraft.core.navigation.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.animecraft.core.navigation.ThanksDialog

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

fun NavGraphBuilder.thanksDialogComposable(
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(
        route = ThanksDialog.route,
        content = content
    )
}