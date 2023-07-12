@file:Suppress("FunctionName", "LongMethod")

package com.animecraft.feature.language

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
import com.anime.animecraft.feature.language.R
import com.anime.animecraft.core.theme.theme.AppTheme
import com.animecraft.model.Language

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

@Composable
fun LanguageDropDown(
    modifier: Modifier = Modifier,
    language: Language,
    expanded: Boolean,
    onClick: () -> Unit,
    languageSelected: (Language) -> Unit = {}
) {
    val iconDegree by animateFloatAsState(
        if (expanded) EXPANDED_ARROW_DEGREE else DEFAULT_ARROW_DEGREE
    )

    Surface(
        modifier = modifier,
        shadowElevation = AppTheme.elevations.medium,
        shape = AppTheme.shapes.medium,
        contentColor = AppTheme.colors.surface,
        color = AppTheme.colors.surface
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = AppTheme.offsets.superGigantic),
                content = {
                    items(languagesList) { language ->
                        LanguageDropDownItem(
                            language = language,
                            onClick = languageSelected
                        )
                    }
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    dimensionResource(id = R.dimen.dropdown_height)
                )
                .background(AppTheme.colors.surface)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(language.iconResId),
                contentDescription = stringResource(R.string.flag_image),
                modifier = Modifier
                    .height(
                        dimensionResource(id = R.dimen.dropdown_icon_size)
                    )
                    .padding(horizontal = AppTheme.offsets.regular)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = language.nameResId),
                style = AppTheme.typography.bodyLarge,
                color = AppTheme.colors.onBackground
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = AppTheme.offsets.regular)
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
            .height(
                dimensionResource(id = R.dimen.dropdown_height)
            )
            .background(AppTheme.colors.surface)
            .clickable { onClick(language) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = language.iconResId),
            contentDescription = stringResource(R.string.flag_image),
            modifier = Modifier
                .height(
                    dimensionResource(id = R.dimen.dropdown_icon_size)
                )
                .padding(horizontal = AppTheme.offsets.regular)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = language.nameResId),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onBackground
        )
    }
}

private const val EXPANDED_ARROW_DEGREE = 180f
private const val DEFAULT_ARROW_DEGREE = 0f
