@file:Suppress("FunctionName")
@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.core.android.extensions.updateLanguage
import ua.anime.animecraft.ui.navigation.AnimeCraftHost
import ua.anime.animecraft.ui.screens.settings.SettingsViewModel
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.utils.DarkModeHandler.darkModeState
import ua.anime.animecraft.ui.utils.DarkModeHandler.updateDarkModeState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val language = settingsViewModel.getSelectedLanguage(Locale.getDefault().language)
        updateLanguage(
            language = language.languageLocale,
            country = language.countryLocale
        )
        setContent {
            val isSystemInDarkMode = isSystemInDarkTheme()
            updateDarkModeState(settingsViewModel.isDarkModeEnabled(isSystemInDarkMode))
            val isDarkTheme by darkModeState.collectLifecycleAwareFlowAsState(isSystemInDarkMode)
            AnimeCraftTheme(darkTheme = isDarkTheme) {
                AnimeCraftApp()
            }
        }
    }

    @Composable
    fun AnimeCraftApp() {
        val navController = rememberAnimatedNavController()
        AnimeCraftHost(navController = navController)
    }
}
