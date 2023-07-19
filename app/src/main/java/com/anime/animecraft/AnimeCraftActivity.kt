@file:Suppress("FunctionName")
@file:OptIn(ExperimentalAnimationApi::class)

package com.anime.animecraft

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
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.android.extensions.toast
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.play.core.splitinstall.SplitInstallManager
import dagger.hilt.android.AndroidEntryPoint
import com.anime.animecraft.ui.utils.DarkModeHandler.darkModeState
import com.animecraft.analytics.api.AnalyticsApi
import com.animecraft.core.navigation.AnimeCraftHost
import com.anime.animecraft.core.theme.theme.AnimeCraftTheme
import javax.inject.Inject

@AndroidEntryPoint
class AnimeCraftActivity : ComponentActivity() {

    @Inject
    lateinit var splitInstallManager: SplitInstallManager

    @Inject
    lateinit var analyticsApiImpl: AnalyticsApi

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
//            updateDarkModeState(animeViewModel.isDarkModeEnabled(isSystemInDarkMode))
            val isDarkTheme by darkModeState.collectAsStateWithLifecycle(isSystemInDarkMode)
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
        /*val language = animeViewModel.getSelectedLanguage(Locale.getDefault().language)
        updateLanguage(
            language = language.languageLocale,
            country = language.countryLocale,
            splitInstallManager = splitInstallManager
        )*/
    }
}
