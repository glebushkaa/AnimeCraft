@file:Suppress("LongMethod", "FunctionName", "LongParameterList")
@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.shareApp
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.buttons.BackButton
import ua.anime.animecraft.ui.dialogs.rating.RatingDialog
import ua.anime.animecraft.ui.navigation.SETTINGS
import ua.anime.animecraft.ui.screens.main.MainViewModel
import ua.anime.animecraft.ui.screens.settings.components.SettingButton
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.theme.AppTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/15/2023.
 */

@Composable
fun SettingsScreen(
    backClicked: () -> Unit = {},
    onLanguageScreenNavigate: () -> Unit = {},
    onReportScreenNavigate: () -> Unit = {},
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val shareAppLink = stringResource(id = R.string.share_app_link)

    var ratingDialogShown by rememberSaveable { mutableStateOf(false) }

    if (ratingDialogShown) {
        RatingDialog(
            onDismissRequest = {
                ratingDialogShown = false
            },
            onRatingDialogCompleted = {
                mainViewModel.setRateDialogCompleted()
            },
            onRatingDialogDisable = {
                mainViewModel.disableRateDialog()
                ratingDialogShown = false
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = AppTheme.colors.background,
        containerColor = AppTheme.colors.background,
        topBar = {
            AppTopBar(
                modifier = Modifier.padding(
                    horizontal = AppTheme.offsets.regular
                ),
                currentScreen = SETTINGS
            )
        },
        content = {
            SettingScreenContent(
                modifier = Modifier.padding(
                    start = AppTheme.offsets.regular,
                    end = AppTheme.offsets.regular,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                backClicked = backClicked,
                onLanguageScreenNavigate = onLanguageScreenNavigate,
                onReportScreenNavigate = onReportScreenNavigate,
                onShareClicked = { context.shareApp(shareAppLink) },
                onFeedbackClicked = { ratingDialogShown = true }
            )
        }
    )
}

@Composable
private fun SettingScreenContent(
    modifier: Modifier = Modifier,
    backClicked: () -> Unit = {},
    onLanguageScreenNavigate: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onReportScreenNavigate: () -> Unit = {},
    onFeedbackClicked: () -> Unit = {},
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val isSystemInDarkMode = isSystemInDarkTheme()
    var downloadHelpDialogSetting by rememberSaveable { mutableStateOf(false) }
    var darkMode by rememberSaveable {
        mutableStateOf(settingsViewModel.isDarkModeEnabled(isSystemInDarkMode))
    }

    Column(modifier = modifier.fillMaxSize()) {
        BackButton(backClicked)
        Text(
            modifier = Modifier.padding(
                vertical = AppTheme.offsets.huge
            ),
            text = stringResource(id = R.string.settings),
            style = AppTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        DarkModeSetting(
            darkMode = darkMode,
            onCheckedChange = { value ->
                darkMode = value
                settingsViewModel.changeDarkMode(value)
            }
        )
        if (settingsViewModel.isDownloadDialogDisabled) {
            Spacer(modifier = Modifier.height(AppTheme.offsets.medium))
            DownloadHelpDialogSetting(
                enabled = downloadHelpDialogSetting,
                onCheckedChange = { value ->
                    settingsViewModel.updateDownloadDialogSetting(!value)
                    downloadHelpDialogSetting = value
                }
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.offsets.regular))
        SettingButton(
            text = stringResource(id = R.string.change_language),
            iconResId = R.drawable.ic_map,
            onClick = onLanguageScreenNavigate
        )
        SettingButton(
            text = stringResource(id = R.string.rate_us),
            iconResId = R.drawable.ic_face_with_heart_eyes,
            onClick = onFeedbackClicked
        )
        SettingButton(
            text = stringResource(id = R.string.send_report),
            iconResId = R.drawable.ic_report,
            onClick = onReportScreenNavigate
        )
        SettingButton(
            text = stringResource(id = R.string.share_app),
            iconResId = R.drawable.ic_share,
            onClick = onShareClicked
        )
        Spacer(modifier = Modifier.weight(1f))
        SettingButton(
            modifier = Modifier.padding(bottom = AppTheme.offsets.large),
            text = stringResource(id = R.string.disable_ad),
            iconResId = R.drawable.ic_disable_ad,
            onClick = {}
        )
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
            modifier = Modifier.size(AppTheme.sizes.screens.settings.checkBoxSize),
            checked = enabled,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.primary,
                uncheckedColor = AppTheme.colors.primary,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(AppTheme.offsets.regular))
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
            modifier = Modifier.size(AppTheme.sizes.screens.settings.checkBoxSize),
            checked = darkMode,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = AppTheme.colors.primary,
                uncheckedColor = AppTheme.colors.primary,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(AppTheme.offsets.regular))
        Text(
            text = stringResource(id = R.string.dark_mode),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onBackground
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
