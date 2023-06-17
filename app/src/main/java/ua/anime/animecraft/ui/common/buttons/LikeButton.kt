@file:Suppress("FunctionName")
@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui.common.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

@Composable
fun LikeButton(favorite: Boolean, likeClick: () -> Unit = {}) {
    AnimatedContent(targetState = favorite, transitionSpec = {
        if (targetState) {
            scaleIn() + fadeIn() with scaleOut()
        } else {
            fadeIn() with fadeOut()
        }
    }) {
        IconButton(
            modifier = Modifier.size(AppTheme.sizes.items.likeButton.size),
            onClick = likeClick
        ) {
            val iconResId = if (favorite) {
                R.drawable.ic_filled_like
            } else {
                R.drawable.ic_like
            }
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(id = R.string.like_icon),
                tint = AppTheme.colors.primary
            )
        }
    }
}
