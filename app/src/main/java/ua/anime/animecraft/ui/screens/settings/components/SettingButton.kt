@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.screens.settings.components

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
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AppTheme
import ua.anime.animecraft.ui.theme.settingButtonElevation
import ua.anime.animecraft.ui.theme.settingButtonShape

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
            .padding(
                top = dimensionResource(id = R.dimen.offset_medium)
            )
            .fillMaxWidth()
            .height(60.dp),
        shadowElevation = settingButtonElevation,
        shape = settingButtonShape,
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
                    .height(24.dp)
                    .padding(horizontal = dimensionResource(id = R.dimen.offset_regular)),
                painter = painterResource(id = iconResId),
                contentDescription = "Rate us button image"
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
