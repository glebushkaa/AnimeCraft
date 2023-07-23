@file:Suppress("LongMethod", "FunctionName", "LongParameterList")
@file:OptIn(ExperimentalMaterial3Api::class)

package com.animecraft.feature.settings

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.android.extensions.shareApp
import com.anime.animecraft.core.components.AppTopBar
import com.anime.animecraft.core.components.buttons.BackButton
import com.anime.animecraft.core.theme.theme.AppTheme
import ua.anime.animecraft.feature.settings.R

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/15/2023.
 */

@Composable
fun SettingsScreen(
    backClicked: () -> Unit = {},
    onLanguageScreenNavigate: () -> Unit = {},
    onReportScreenNavigate: () -> Unit = {},
    onFavoritesScreenNavigate: () -> Unit = {},
    onRatingDialogNavigate: () -> Unit = {},
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val shareAppLink = stringResource(id = R.string.share_app_link)
    val isSystemInDarkMode = isSystemInDarkTheme()

    val state by settingsViewModel.screenState.collectAsStateWithLifecycle(
        initialValue = SettingsScreenState()
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
                settingsIconFilled = true,
                likeClicked = onFavoritesScreenNavigate
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
                onDarkModeChanged = { settingsViewModel.updateDarkMode() },
                darkMode = state.darkModeEnabled,
                downloadHelpDialogCheck = state.downloadDialogDisabled,
                onDownloadSettingChanged = settingsViewModel::updateDownloadDialogSetting,
                downloadDialogSettingVisible = state.downloadDialogDisabled,
                onFeedbackClicked = onRatingDialogNavigate
            )
        }
    )

    LaunchedEffect(key1 = Unit) {
        settingsViewModel.saveSystemDarkMode(isSystemInDarkMode)
    }
}

@Composable
private fun SettingScreenContent(
    modifier: Modifier = Modifier,
    backClicked: () -> Unit = {},
    onLanguageScreenNavigate: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onReportScreenNavigate: () -> Unit = {},
    onFeedbackClicked: () -> Unit = {},
    onDarkModeChanged: (Boolean) -> Unit = {},
    onDownloadSettingChanged: () -> Unit = {},
    darkMode: Boolean = false,
    downloadHelpDialogCheck: Boolean = false,
    downloadDialogSettingVisible: Boolean = false
) {
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
            onCheckedChange = onDarkModeChanged
        )
        if (downloadDialogSettingVisible) {
            Spacer(modifier = Modifier.height(AppTheme.offsets.medium))
            DownloadHelpDialogSetting(
                enabled = downloadHelpDialogCheck,
                onCheckedChange = { onDownloadSettingChanged() }
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
            modifier = Modifier.size(
                dimensionResource(id = R.dimen.setting_check_box_size)
            ),
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
            modifier = Modifier.size(
                dimensionResource(id = R.dimen.setting_check_box_size)
            ),
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
