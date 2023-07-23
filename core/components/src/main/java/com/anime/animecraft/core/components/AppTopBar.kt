@file:Suppress("FunctionName")

package com.anime.animecraft.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.anime.animecraft.core.theme.theme.AppTheme
import ua.anime.animecraft.core.components.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    likeIconFilled: Boolean = false,
    settingsIconFilled: Boolean = false,
    settingsClicked: () -> Unit = {},
    likeClicked: () -> Unit = {}
) {
    val appName = stringResource(R.string.app_name)

    val likeIcon =
        if (likeIconFilled) R.drawable.ic_filled_like else R.drawable.ic_like
    val settingsIcon =
        if (settingsIconFilled) R.drawable.ic_filled_settings else R.drawable.ic_settings
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = appName.uppercase(),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        IconButton(onClick = likeClicked) {
            Icon(
                painter = painterResource(id = likeIcon),
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        }
        IconButton(onClick = settingsClicked) {
            Icon(
                painter = painterResource(id = settingsIcon),
                contentDescription = null,
                tint = AppTheme.colors.primary
            )
        }
    }
}
