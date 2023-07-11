@file:Suppress("FunctionName")

package ua.animecraft.feature.rating

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import ua.animecraft.core.components.AnimatedScaleDialogContent
import ua.animecraft.core.components.PRE_DISMISS_DELAY
import ua.animecraft.feature.rating.thanks.ThanksDialogContent
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
            delay(ua.animecraft.core.components.PRE_DISMISS_DELAY)
            onDismissRequest()
        }
    }

    Dialog(
        onDismissRequest = { isDismissed = true },
        properties = AppTheme.dialogProperties
    ) {
        ua.animecraft.core.components.AnimatedScaleDialogContent(content = {
            ThanksDialogContent(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.large
                ),
                onDismissRequest = { isDismissed = true }
            )
        }, isDismissed = isDismissed)
    }
}
