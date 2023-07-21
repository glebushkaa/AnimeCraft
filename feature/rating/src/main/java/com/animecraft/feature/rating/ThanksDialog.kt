@file:Suppress("FunctionName")

package com.animecraft.feature.rating

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.anime.animecraft.core.components.AnimatedScaleDialogContent
import com.anime.animecraft.core.components.PRE_DISMISS_DELAY
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.feature.rating.thanks.ThanksDialogContent
import kotlinx.coroutines.delay

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
                    horizontal = AppTheme.offsets.large
                ),
                onDismissRequest = { isDismissed = true }
            )
        }, isDismissed = isDismissed)
    }
}
