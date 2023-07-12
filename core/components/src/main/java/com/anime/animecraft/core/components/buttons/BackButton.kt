@file:Suppress("FunctionName")

package com.anime.animecraft.core.components.buttons

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
import androidx.compose.ui.unit.dp
import com.anime.animecraft.core.components.R
import com.anime.animecraft.core.theme.theme.AppTheme

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
