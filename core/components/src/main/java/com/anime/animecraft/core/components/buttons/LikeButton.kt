@file:Suppress("FunctionName")
@file:OptIn(ExperimentalAnimationApi::class)

package com.anime.animecraft.core.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.anime.animecraft.core.components.R
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.animecraft.common.HUNDRED_MILLIS
import com.animecraft.animecraft.common.THREE_HUNDRED_MILLIS

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/28/2023
 */

@Composable
fun LikeButton(favorite: Boolean, likeClick: () -> Unit = {}) {
    IconButton(
        modifier = Modifier.size(
            dimensionResource(id = R.dimen.like_icon_size)
        ),
        onClick = likeClick
    ) {
        AnimatedContent(
            targetState = favorite,
            transitionSpec = {
                if (targetState) favoriteContentTransform else nonFavoriteContentTransform
            }
        ) {
            val iconResId = if (it) R.drawable.ic_filled_like else R.drawable.ic_like

            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(id = R.string.like_icon),
                tint = AppTheme.colors.primary
            )
        }
    }
}

private const val INITIAL_FAVORITE_SCALE_IN_VALUE = 0.6f
private const val TARGET_FAVORITE_SCALE_OUT_VALUE = 1f

private const val INITIAL_NON_FAVORITE_SCALE_IN_VALUE = 0.4f
private const val TARGET_NON_FAVORITE_SCALE_OUT_VALUE = 0.8f

private val defaultAnimSpec = tween<Float>(durationMillis = THREE_HUNDRED_MILLIS.toInt())
private val fastAnimSpec = tween<Float>(durationMillis = HUNDRED_MILLIS.toInt())

private val favoriteContentTransform = scaleIn(
    animationSpec = defaultAnimSpec,
    initialScale = INITIAL_FAVORITE_SCALE_IN_VALUE
) with scaleOut(
    animationSpec = fastAnimSpec,
    targetScale = TARGET_FAVORITE_SCALE_OUT_VALUE
)

private val nonFavoriteContentTransform = scaleIn(
    animationSpec = defaultAnimSpec,
    initialScale = INITIAL_NON_FAVORITE_SCALE_IN_VALUE
) with scaleOut(
    animationSpec = fastAnimSpec,
    targetScale = TARGET_NON_FAVORITE_SCALE_OUT_VALUE
)
