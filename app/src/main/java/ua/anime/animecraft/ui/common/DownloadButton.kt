@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun DownloadButton(modifier: Modifier, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AppTheme.colors.primary
        )
    ) {
        Text(
            text = stringResource(id = R.string.download),
            style = AppTheme.typography.bodyLargeBold,
            color = AppTheme.colors.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DownloadButtonPreview() {
    AnimeCraftTheme {
        DownloadButton(
            modifier = Modifier
                .advanceShadow(borderRadius = 60.dp, blurRadius = 4.dp)
                .fillMaxWidth()
                .height(40.dp)
        ) {}
    }
}
