@file:Suppress("LongParameterList", "FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.animecraft.feature.language

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.android.extensions.updateLanguage
import com.anime.animecraft.core.components.AppTopBar
import com.anime.animecraft.core.components.buttons.BackButton
import com.anime.animecraft.core.theme.theme.AppTheme
import com.anime.animecraft.feature.language.R
import com.animecraft.model.Language

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/1/2023
 */

@Composable
fun LanguageScreen(
    onBackClicked: () -> Unit = {},
    onLikeNavigate: () -> Unit = {},
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state by languageViewModel.screenState.collectAsStateWithLifecycle(
        LanguageScreenState()
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                settingsClicked = onBackClicked,
                likeClicked = onLikeNavigate
            )
        },
        content = {
            LanguageScreenContent(
                modifier = Modifier.padding(
                    start = AppTheme.offsets.regular,
                    end = AppTheme.offsets.regular,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                changeEnabled = state.changeEnabled,
                selectedLanguage = state.selectedLanguage,
                dropdownExpanded = state.dropdownExpanded,
                onDropdownBaseItemClick = languageViewModel::updateDropdownState,
                onLanguageSelected = languageViewModel::updateSelectedLanguage,
                onConfirmClicked = {
                    languageViewModel.changeLanguage()
                    val locale = state.selectedLanguage.locale
                    context.updateLanguage(locale)
                },
                onBackClicked = onBackClicked
            )
        }
    )
}

@Composable
private fun LanguageScreenContent(
    modifier: Modifier = Modifier,
    selectedLanguage: Language,
    dropdownExpanded: Boolean,
    changeEnabled: Boolean,
    onDropdownBaseItemClick: () -> Unit,
    onLanguageSelected: (Language) -> Unit,
    onConfirmClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BackButton(onBackClicked)
        Text(
            modifier = Modifier.padding(
                top = AppTheme.offsets.average
            ),
            text = stringResource(id = R.string.language),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        Box(
            modifier = Modifier.padding(
                top = AppTheme.offsets.huge
            )
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.padding(top = AppTheme.offsets.ultraGigantic),
                visible = changeEnabled
            ) {
                LanguageConfirmButton(onClick = onConfirmClicked)
            }
            LanguageDropDown(
                language = selectedLanguage,
                expanded = dropdownExpanded,
                onClick = onDropdownBaseItemClick,
                languageSelected = onLanguageSelected
            )
        }
    }
}

@Composable
private fun LanguageConfirmButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(
                dimensionResource(id = R.dimen.confirm_button_height)
            ),
        shape = AppTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = AppTheme.elevations.small
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.confirm),
            style = AppTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = AppTheme.colors.secondary
        )
    }
}
