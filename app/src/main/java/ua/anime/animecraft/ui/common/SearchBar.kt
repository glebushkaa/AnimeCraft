@file:Suppress("FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.common

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
import androidx.compose.ui.tooling.preview.Preview
import ua.anime.animecraft.R
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme
import ua.anime.animecraft.ui.theme.searchBarShape

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/7/2023.
 */

@Composable
fun SearchBar(modifier: Modifier = Modifier, value: String, onValueChanged: (String) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.search_bar_height))
            .clip(searchBarShape)
            .border(
                color = AppTheme.colors.primary,
                width = dimensionResource(id = R.dimen.search_bar_border_width),
                shape = searchBarShape
            )
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = value,
            singleLine = true,
            onValueChange = onValueChanged,
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

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    AnimeCraftTheme(darkTheme = false) {
        SearchBar(value = "") {}
    }
}
