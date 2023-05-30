@file:Suppress("LongMethod", "FunctionName")
@file:OptIn(ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.anime.animecraft.R
import ua.anime.animecraft.core.activityholder.CurrentActivityHolder
import ua.anime.animecraft.ui.SETTINGS
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.BackButton
import ua.anime.animecraft.ui.model.Language
import ua.anime.animecraft.ui.screens.settings.components.LanguageDropDown
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/15/2023.
 */

@Composable
fun SettingsScreen(backClicked: () -> Unit = {}) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.offset_regular)
                ),
                currentScreen = SETTINGS
            )
        },
        content = {
            SettingScreenContent(
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.offset_regular),
                    end = dimensionResource(id = R.dimen.offset_regular),
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                backClicked = backClicked
            )
        }
    )
}

@Composable
private fun SettingScreenContent(
    modifier: Modifier = Modifier,
    backClicked: () -> Unit = {},
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val isSystemInDarkMode = isSystemInDarkTheme()
    var selectedLanguage by remember { mutableStateOf(settingsViewModel.getSelectedLanguage()) }
    var newSkinsNotification by rememberSaveable { mutableStateOf(false) }
    var downloadHelpDialogSetting by rememberSaveable { mutableStateOf(false) }
    var darkMode by rememberSaveable {
        mutableStateOf(settingsViewModel.isDarkModeEnabled() ?: isSystemInDarkMode)
    }
    var expandedDropDown by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        BackButton(backClicked)
        Text(
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.offset_huge)
            ),
            text = stringResource(id = R.string.settings),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        NotificationNewSkinsSetting(
            modifier = Modifier.padding(
                bottom = dimensionResource(id = R.dimen.offset_medium)
            ),
            newSkinsNotification = newSkinsNotification,
            onChanged = { value -> newSkinsNotification = value }
        )
        DarkModeSetting(
            darkMode = darkMode,
            onCheckedChange = { value ->
                darkMode = value
                settingsViewModel.changeDarkMode(value)
            }
        )
        if (settingsViewModel.isDownloadDialogDisabled) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_medium)))
            DownloadHelpDialogSetting(
                enabled = downloadHelpDialogSetting,
                onCheckedChange = { value ->
                    settingsViewModel.updateDownloadDialogSetting(!value)
                    downloadHelpDialogSetting = value
                }
            )
        }
        Text(
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.offset_average)
            ),
            text = stringResource(id = R.string.language),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        LanguageDropDown(
            language = selectedLanguage,
            expanded = expandedDropDown,
            onClick = { value -> expandedDropDown = value },
            languageSelected = { value -> selectedLanguage = value }
        )
        if (selectedLanguage != settingsViewModel.initialLanguage && !expandedDropDown) {
            LanguageConfirmButton(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.offset_regular)
                ),
                selectedLanguage = selectedLanguage
            )
        }
    }
}

@Composable
private fun DownloadHelpDialogSetting(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(24.dp),
            checked = enabled,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.primary,
                uncheckedColor = AppTheme.colors.primary,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.offset_regular)))
        Text(
            text = stringResource(id = R.string.download_help_dialog),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onBackground
        )
    }
}

@Composable
private fun DarkModeSetting(
    modifier: Modifier = Modifier,
    darkMode: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(24.dp),
            checked = darkMode,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.primary,
                uncheckedColor = AppTheme.colors.primary,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.offset_regular)))
        Text(
            text = stringResource(id = R.string.dark_mode),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onBackground
        )
    }
}

@Composable
private fun NotificationNewSkinsSetting(
    modifier: Modifier = Modifier,
    newSkinsNotification: Boolean,
    onChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(24.dp),
            checked = newSkinsNotification,
            onCheckedChange = onChanged,
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.primary,
                uncheckedColor = AppTheme.colors.primary,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.offset_regular)))
        Text(
            text = stringResource(id = R.string.notifications_new_skins),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onBackground
        )
    }
}

@Composable
private fun LanguageConfirmButton(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    selectedLanguage: Language
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.primary
        ),
        onClick = {
            settingsViewModel.updateLanguagePreference(selectedLanguage.languageLocale)
            CurrentActivityHolder.getCurrentActivity()?.recreate()
        }
    ) {
        Text(
            text = stringResource(R.string.confirm),
            style = AppTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = AppTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    AnimeCraftTheme(darkTheme = true) {
        SettingsScreen({})
    }
}
