@file:Suppress("FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.anime.animecraft.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.anime.animecraft.core.components.R
import com.anime.animecraft.core.theme.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                dimensionResource(
                    id = com.anime.animecraft.core.common.android.R.dimen.search_bar_height
                )
            )
            .clip(AppTheme.shapes.huge)
            .border(
                color = AppTheme.colors.primary,
                width = AppTheme.strokes.small,
                shape = AppTheme.shapes.huge
            )
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = value,
            singleLine = true,
            onValueChange = {
                val query = if (it.length > MAX_SEARCH_QUERY_LENGTH) {
                    it.substring(0, MAX_SEARCH_QUERY_LENGTH)
                } else {
                    it
                }
                onValueChanged(query)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.search_icon),
                    tint = AppTheme.colors.primary
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.find_skin),
                    style = AppTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.tertiary
                )
            },
            textStyle = AppTheme.typography.bodyLargeBold,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = AppTheme.colors.primary,
                textColor = AppTheme.colors.primary
            )
        )
    }
}

private const val MAX_SEARCH_QUERY_LENGTH = 100
