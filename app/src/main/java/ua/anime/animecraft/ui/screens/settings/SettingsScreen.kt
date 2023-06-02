@file:Suppress("LongMethod", "FunctionName", "LongParameterList")
@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package ua.anime.animecraft.ui.screens.settings

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.anime.animecraft.R
import ua.anime.animecraft.core.android.extensions.sendFeedback
import ua.anime.animecraft.core.android.extensions.shareApp
import ua.anime.animecraft.core.common.ONE_SECOND
import ua.anime.animecraft.ui.common.AppTopBar
import ua.anime.animecraft.ui.common.buttons.BackButton
import ua.anime.animecraft.ui.dialogs.rating.ShareFeedbackDialog
import ua.anime.animecraft.ui.navigation.SETTINGS
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
    onReportScreenNavigate: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var shareFeedbackSelected by rememberSaveable { mutableStateOf(false) }
    val shareAppLink = stringResource(id = R.string.share_app_link)

    AnimatedVisibility(visible = shareFeedbackSelected) {
        ShareFeedbackDialog(
            onDismissRequest = {
                shareFeedbackSelected = false
            },
            onFeedbackSent = {
                scope.launch {
                    context.sendFeedback(it)
                    delay(ONE_SECOND)
                    shareFeedbackSelected = false
                }
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
                backClicked = backClicked,
                onLanguageScreenNavigate = onLanguageScreenNavigate,
                onReportScreenNavigate = onReportScreenNavigate,
                onShareClicked = { context.shareApp(shareAppLink) },
                onFeedbackClicked = { shareFeedbackSelected = true }
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

    var newSkinsNotification by rememberSaveable { mutableStateOf(false) }
    var downloadHelpDialogSetting by rememberSaveable { mutableStateOf(false) }
    var darkMode by rememberSaveable {
        mutableStateOf(settingsViewModel.isDarkModeEnabled() ?: isSystemInDarkMode)
    }

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
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.offset_regular)))
        SettingButton(
            text = stringResource(id = R.string.change_language),
            iconResId = R.drawable.ic_map,
            onClick = onLanguageScreenNavigate
        )
        SettingButton(
            text = stringResource(id = R.string.send_feedback),
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
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.offset_large)),
            text = stringResource(id = R.string.disable_ad),
            iconResId = R.drawable.ic_disable_ad,
            onClick = {  }
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

@Preview
@Composable
fun SettingsScreenPreview() {
    AnimeCraftTheme(darkTheme = true) {
        SettingsScreen({})
    }
}
