package com.animecraft.core.navigation.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.animecraft.core.navigation.RatingDialog


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

fun NavGraphBuilder.ratingDialogComposable(
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(
        route = RatingDialog.route,
        content = content
    )
}