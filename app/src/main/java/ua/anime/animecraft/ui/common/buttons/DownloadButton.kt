@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.common.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun DownloadButton(modifier: Modifier, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(AppTheme.offsets.default),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AppTheme.colors.primary
        )
    ) {
        Text(
            text = stringResource(id = R.string.download),
            style = AppTheme.typography.bodyLargeBold,
            color = AppTheme.colors.secondary
        )
    }
}
