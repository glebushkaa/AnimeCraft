@file:Suppress("FunctionName", "MagicNumber")

package ua.anime.animecraft.ui.screens.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.languagesList
import ua.anime.animecraft.ui.model.Language
import ua.anime.animecraft.ui.theme.AppTheme
import ua.anime.animecraft.ui.theme.settingButtonElevation
import ua.anime.animecraft.ui.theme.settingButtonShape

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

@Composable
fun LanguageDropDown(
    modifier: Modifier = Modifier,
    language: Language,
    expanded: Boolean,
    onClick: (Boolean) -> Unit,
    languageSelected: (Language) -> Unit = {}
) {
    val iconDegree by animateFloatAsState(if (expanded) 180f else 0f)

    Surface(
        modifier = modifier,
        shadowElevation = settingButtonElevation,
        shape = settingButtonShape,
        contentColor = AppTheme.colors.surface,
        color = AppTheme.colors.surface
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 60.dp),
                content = {
                    items(languagesList) { language ->
                        LanguageDropDownItem(language = language) {
                            languageSelected(language)
                            onClick(false)
                        }
                    }
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(AppTheme.colors.surface)
                .clickable { onClick(!expanded) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(language.iconResId),
                contentDescription = stringResource(R.string.flag_image),
                modifier = Modifier
                    .height(24.dp)
                    .padding(horizontal = dimensionResource(id = R.dimen.offset_regular))
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = language.nameResId),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colors.onBackground
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.offset_regular))
                    .rotate(iconDegree),
                painter = painterResource(id = R.drawable.ic_drop_down_arrow),
                contentDescription = stringResource(R.string.drop_down_arrow),
                tint = AppTheme.colors.primary
            )
        }
    }
}

@Composable
private fun LanguageDropDownItem(
    language: Language,
    onClick: (Language) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(AppTheme.colors.surface)
            .clickable { onClick(language) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = language.iconResId),
            contentDescription = stringResource(R.string.flag_image),
            modifier = Modifier
                .height(24.dp)
                .padding(horizontal = dimensionResource(id = R.dimen.offset_regular))
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = language.nameResId),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onBackground
        )
    }
}
