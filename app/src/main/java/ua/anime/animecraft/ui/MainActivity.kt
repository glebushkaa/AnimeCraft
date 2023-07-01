@file:Suppress("FunctionName")
@file:OptIn(ExperimentalAnimationApi::class)

package ua.anime.animecraft.ui

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.play.core.splitinstall.SplitInstallManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject
import ua.anime.animecraft.AnimeCraftApp
import ua.anime.animecraft.R
import ua.anime.animecraft.analytics.impl.AnalyticsApiImpl
import ua.anime.animecraft.core.android.extensions.collectLifecycleAwareFlowAsState
import ua.anime.animecraft.core.android.extensions.toast
import ua.anime.animecraft.core.android.extensions.updateLanguage
import ua.anime.animecraft.ui.navigation.AnimeCraftHost
import ua.anime.animecraft.ui.screens.settings.SettingsViewModel
import ua.anime.animecraft.ui.theme.AnimeCraftTheme
import ua.anime.animecraft.ui.utils.DarkModeHandler.darkModeState
import ua.anime.animecraft.ui.utils.DarkModeHandler.updateDarkModeState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var splitInstallManager: SplitInstallManager

    @Inject
    lateinit var analyticsApiImpl: AnalyticsApiImpl

    private val writePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            (applicationContext as? AnimeCraftApp)?.startSkinWork()
        } else {
            toast(getString(R.string.we_cant_load_without_permission))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentLanguage()
        if (SDK_INT <= Build.VERSION_CODES.Q) writePermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
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
        navController.addOnDestinationChangedListener { _, destination, _ ->
            analyticsApiImpl.setCurrentScreen(destination.route ?: "")
        }
        AnimeCraftHost(navController = navController)
    }

    private fun setCurrentLanguage() {
        val language = settingsViewModel.getSelectedLanguage(Locale.getDefault().language)
        updateLanguage(
            language = language.languageLocale,
            country = language.countryLocale,
            splitInstallManager = splitInstallManager
        )
    }
}
