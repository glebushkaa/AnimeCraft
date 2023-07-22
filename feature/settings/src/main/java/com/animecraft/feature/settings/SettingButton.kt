@file:Suppress("FunctionName")

package com.animecraft.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.anime.animecraft.core.theme.theme.AppTheme
import ua.anime.animecraft.feature.settings.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/1/2023
 */

@Composable
fun SettingButton(
    text: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .padding(top = AppTheme.offsets.medium)
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.setting_button_height)
            ),
        shadowElevation = AppTheme.elevations.medium,
        shape = AppTheme.shapes.medium,
        contentColor = AppTheme.colors.surface,
        color = AppTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.setting_image_width)
                    )
                    .padding(horizontal = AppTheme.offsets.regular),
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(R.string.rate_us_button_image)
            )
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colors.onBackground
            )
        }
    }
}
