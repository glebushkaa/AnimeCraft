@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.common.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/31/2023
 */

@Composable
fun FilledDialogButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    textStyle: TextStyle = AppTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        shape = AppTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.primary)
    ) {
        Text(
            text = text,
            color = AppTheme.colors.secondary,
            style = textStyle
        )
    }
}
