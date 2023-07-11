@file:Suppress("FunctionName", "MagicNumber")
@file:OptIn(ExperimentalFoundationApi::class)

package ua.animecraft.feature.download.skin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/16/2023
 */

@Composable
fun PagerItemIndicator(
    modifier: Modifier = Modifier,
    circleRadius: Float = 24f,
    currentItem: Int = 0
) {
    val dotColor = AppTheme.colors.primary
    val backgroundColor = AppTheme.colors.tertiary

    Canvas(
        modifier = modifier
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

            indicatorDot(
                color = dotColor,
                radius = circleRadius,
                selected = currentItem == 0,
                center = center.copy(x = center.x - (circleRadius * 2.5f))
            )

            indicatorDot(
                color = dotColor,
                radius = circleRadius,
                selected = currentItem in 1..2,
                center = center
            )
            indicatorDot(
                color = dotColor,
                radius = circleRadius,
                selected = currentItem == 3,
                center = center.copy(x = center.x + (circleRadius * 2.5f))
            )
        }
    )
}

private fun DrawScope.indicatorDot(
    color: Color = Color.LightGray,
    radius: Float = 24f,
    selected: Boolean = false,
    center: Offset
) {
    val alpha = if (selected) SELECTED_DOT_ALPHA else UNSELECTED_DOT_ALPHA

    drawCircle(
        color = color,
        radius = radius,
        style = Fill,
        alpha = alpha,
        center = center
    )
}

private const val POINTS_COUNT = 3
private const val SELECTED_DOT_ALPHA = 1f
private const val UNSELECTED_DOT_ALPHA = 0.3f
