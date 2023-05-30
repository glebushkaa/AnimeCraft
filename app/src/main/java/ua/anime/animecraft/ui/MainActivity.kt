@file:Suppress("FunctionName")

package ua.anime.animecraft.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.anime.animecraft.core.android.extensions.updateLanguage
import ua.anime.animecraft.ui.navigation.AnimeCraftHost
import ua.anime.animecraft.ui.screens.settings.SettingsViewModel
import ua.anime.animecraft.ui.theme.AnimeCraftTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val language = settingsViewModel.getSelectedLanguage()
        updateLanguage(language.languageLocale, language.countryLocale)
        setContent {
            AnimeCraftTheme {
                AnimeCraftApp()
            }
        }
    }

    @Composable
    fun AnimeCraftApp() {
        val navController = rememberNavController()
        AnimeCraftHost(navController = navController)
    }
}
