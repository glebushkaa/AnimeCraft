@file:Suppress("LongParameterList", "FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.animecraft.feature.language

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Locale
import ua.anime.animecraft.R
import com.animecraft.core.android.activityholder.CurrentActivityHolder
import ua.animecraft.core.components.AppTopBar
import ua.animecraft.core.components.buttons.BackButton
import ua.anime.animecraft.ui.model.Language
import ua.anime.animecraft.ui.navigation.LANGUAGE_SETTINGS
import ua.anime.animecraft.ui.screens.settings.components.LanguageDropDown
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/1/2023
 */

@Composable
fun LanguageScreen(
    onBackClicked: () -> Unit = {},
    onLikeNavigate: () -> Unit = {},
    settingsViewModel: ua.animecraft.feature.settings.SettingsViewModel = hiltViewModel()
) {
    val localeLanguage = Locale.getDefault().language
    var selectedLanguage by remember {
        mutableStateOf(settingsViewModel.getSelectedLanguage(localeLanguage))
    }
    var expandedDropDown by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            ua.animecraft.core.components.AppTopBar(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                currentScreen = LANGUAGE_SETTINGS,
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
                selectedLanguage = selectedLanguage,
                expandedDropDown = expandedDropDown,
                initialLanguage = settingsViewModel.initialLanguage,
                onExpandedDropDownChanged = { value -> expandedDropDown = value },
                onLanguageSelected = { value -> selectedLanguage = value },
                onConfirmClicked = {
                    settingsViewModel.updateLanguagePreference(selectedLanguage.languageLocale)
                    com.animecraft.core.android.activityholder.CurrentActivityHolder.getCurrentActivity()?.recreate()
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
    initialLanguage: Language?,
    expandedDropDown: Boolean,
    onExpandedDropDownChanged: (Boolean) -> Unit,
    onLanguageSelected: (Language) -> Unit,
    onConfirmClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ua.animecraft.core.components.buttons.BackButton(onBackClicked)
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
            if (selectedLanguage != initialLanguage && !expandedDropDown) {
                LanguageConfirmButton(
                    modifier = Modifier.padding(top = AppTheme.offsets.ultraGigantic),
                    onClick = onConfirmClicked
                )
            }
            LanguageDropDown(
                language = selectedLanguage,
                expanded = expandedDropDown,
                onClick = { value -> onExpandedDropDownChanged(value) },
                languageSelected = { value -> onLanguageSelected(value) }
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
            .height(AppTheme.sizes.screens.language.confirmButtonHeight),
        shape = AppTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = AppTheme.elevations.small),
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
