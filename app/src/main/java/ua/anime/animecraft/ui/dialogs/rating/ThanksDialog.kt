@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.dialogs.rating

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.window.Dialog
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.dialogs.rating.thanks.ThanksDialogContent
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/1/2023
 */

@Composable
fun ThanksDialog(
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = AppTheme.dialogProperties
    ) {
        ThanksDialogContent(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.offset_large)
            ),
            onDismissRequest = onDismissRequest
        )
    }
}
