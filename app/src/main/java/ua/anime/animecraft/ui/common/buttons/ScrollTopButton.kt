@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.common.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/10/2023
 */

@Composable
fun ScrollTopButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = modifier.padding(bottom = AppTheme.offsets.regular),
        containerColor = AppTheme.colors.primary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = AppTheme.elevations.medium
        ),
        shape = AppTheme.shapes.gigantic,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_top),
            contentDescription = null,
            tint = AppTheme.colors.secondary
        )
    }
}