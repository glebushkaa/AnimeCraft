@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.common

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
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.FAVORITES
import ua.anime.animecraft.ui.SETTINGS
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    settingsClicked: () -> Unit = {},
    likeClicked: () -> Unit = {},
    currentScreen: String
) {
    val likeIcon =
        if (currentScreen == FAVORITES) R.drawable.ic_filled_like else R.drawable.ic_like
    val settingsIcon =
        if (currentScreen == SETTINGS) R.drawable.ic_filled_settings else R.drawable.ic_settings
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.app_name).uppercase(),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        IconButton(onClick = likeClicked) {
            Icon(
                painter = painterResource(id = likeIcon),
                contentDescription = stringResource(id = R.string.like_icon),
                tint = AppTheme.colors.primary
            )
        }
        IconButton(onClick = settingsClicked) {
            Icon(
                painter = painterResource(id = settingsIcon),
                contentDescription = stringResource(id = R.string.settings_icon),
                tint = AppTheme.colors.primary
            )
        }
    }
}
