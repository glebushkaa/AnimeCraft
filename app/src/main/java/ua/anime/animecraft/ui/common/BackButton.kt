package ua.anime.animecraft.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/12/2023.
 */

@Composable
fun BackButton(onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.wrapContentSize(),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back),
            tint = AppTheme.colors.primary
        )
        Text(
            text = stringResource(id = R.string.explore),
            style = AppTheme.typography.titleMedium.copy(
                color = AppTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
fun BackButtonPreview() {
    AnimeCraftTheme {
        BackButton {}
    }
}