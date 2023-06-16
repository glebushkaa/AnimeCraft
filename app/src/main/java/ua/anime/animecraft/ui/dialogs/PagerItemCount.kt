@file:OptIn(ExperimentalFoundationApi::class)

package ua.anime.animecraft.ui.dialogs

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/16/2023
 */

@Composable
fun PagerItemCount(
    modifier: Modifier = Modifier,
    circleRadius: Float = 24f,
    currentItem: Int = 0
) {
    val firstPointAnimatedAlpha by animateFloatAsState(targetValue = if (currentItem == 0) 1f else 0.3f)
    val secondPointAnimatedAlpha by animateFloatAsState(targetValue = if (currentItem in 1..2) 1f else 0.3f)
    val thirdPointAnimatedAlpha by animateFloatAsState(targetValue = if (currentItem == 3) 1f else 0.3f)
    val dotColor = AppTheme.colors.primary
    val backgroundColor = AppTheme.colors.tertiary

    Canvas(modifier = modifier
        .width((circleRadius * POINTS_COUNT).dp)
        .height(circleRadius.dp),
        onDraw = {
            drawRoundRect(
                color = backgroundColor,
                size = size,
                alpha = 0.6f,
                style = Fill,
                cornerRadius = CornerRadius(size.height / 2)
            )

            drawCircle(
                color = dotColor,
                radius = circleRadius,
                style = Fill,
                alpha = firstPointAnimatedAlpha,
                center = center.copy(x = center.x - (circleRadius * 2.5f))
            )

            drawCircle(
                color = dotColor,
                radius = circleRadius,
                style = Fill,
                alpha = secondPointAnimatedAlpha,
                center = center
            )
            drawCircle(
                color = dotColor,
                radius = circleRadius,
                style = Fill,
                alpha = thirdPointAnimatedAlpha,
                center = center.copy(x = center.x + (circleRadius * 2.5f))
            )
        })
}

@Composable
@Preview
fun PagerItemCountPreview() {
    AnimeCraftTheme {
        PagerItemCount()
    }
}

private const val POINTS_COUNT = 3