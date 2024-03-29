package com.anime.animecraft.core.theme.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

data class Shape(
    val tiny: RoundedCornerShape = RoundedCornerShape(4.dp),
    val small: RoundedCornerShape = RoundedCornerShape(8.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val huge: RoundedCornerShape = RoundedCornerShape(20.dp),
    val gigantic: RoundedCornerShape = RoundedCornerShape(24.dp),
    val customShapes: CustomShapes = CustomShapes()
)
