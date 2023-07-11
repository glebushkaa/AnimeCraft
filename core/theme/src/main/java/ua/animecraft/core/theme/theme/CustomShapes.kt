package ua.animecraft.core.theme.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

data class CustomShapes(
    val infoCardShape: RoundedCornerShape = RoundedCornerShape(
        bottomEnd = 20.dp,
        bottomStart = 20.dp
    )
)
