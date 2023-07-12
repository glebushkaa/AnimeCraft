@file:Suppress("FunctionName")

package com.anime.animecraft.core.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.anime.animecraft.core.components.R
import com.anime.animecraft.core.theme.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

@Composable
fun DontShowAgainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier.clickable { onClick() },
        text = stringResource(R.string.dont_show_this_again),
        color = AppTheme.colors.onBackground.copy(alpha = 0.6f),
        style = AppTheme.typography.bodySmall.copy(
            textDecoration = TextDecoration.Underline
        )
    )
}
