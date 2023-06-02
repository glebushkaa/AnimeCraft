@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.dialogs.rating

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.dialogs.component.AnimatedScaleDialogContent
import ua.anime.animecraft.ui.dialogs.component.PRE_DISMISS_DELAY
import ua.anime.animecraft.ui.dialogs.rating.thanks.ThanksDialogContent
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/1/2023
 */

@Composable
fun ThanksDialog(
    onDismissRequest: () -> Unit = {}
) {
    var isDismissed by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isDismissed) {
        if (isDismissed) {
            delay(PRE_DISMISS_DELAY)
            onDismissRequest()
        }
    }

    Dialog(
        onDismissRequest = { isDismissed = true },
        properties = AppTheme.dialogProperties
    ) {
        AnimatedScaleDialogContent(content = {
            ThanksDialogContent(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.offset_large)
                ),
                onDismissRequest = { isDismissed = true }
            )
        }, isDismissed = isDismissed)
    }
}
