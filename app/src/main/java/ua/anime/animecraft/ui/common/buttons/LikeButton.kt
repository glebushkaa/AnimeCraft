@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.common.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

@Composable
fun LikeButton(favorite: Boolean, likeClick: () -> Unit = {}) {
    IconButton(modifier = Modifier.size(24.dp), onClick = likeClick) {
        val iconResId = if (favorite) R.drawable.ic_filled_like else R.drawable.ic_like
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = stringResource(id = R.string.like_icon),
            tint = AppTheme.colors.primary
        )
    }
}
