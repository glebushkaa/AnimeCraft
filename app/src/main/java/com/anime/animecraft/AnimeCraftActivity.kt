@file:Suppress("FunctionName")
@file:OptIn(ExperimentalAnimationApi::class)

package com.anime.animecraft

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.anime.animecraft.core.android.extensions.collectAsStateWithLifecycle
import com.anime.animecraft.core.android.extensions.toast
import com.anime.animecraft.core.theme.theme.AnimeCraftTheme
import com.anime.animecraft.utils.DarkModeHandler.darkModeState
import com.animecraft.core.navigation.AnimeCraftHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.anime.animecraft.R

@AndroidEntryPoint
class AnimeCraftActivity : AppCompatActivity() {

    private val animeViewModel: AnimeViewModel by viewModels()

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
        if (SDK_INT <= Build.VERSION_CODES.Q) writePermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
        setContent {
            val isSystemInDarkMode = isSystemInDarkTheme()
            animeViewModel.setupDarkMode(isSystemInDarkMode)
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
            animeViewModel.sendScreenAnalytic(destination.route ?: "")
        }
        AnimeCraftHost(navController = navController)
    }
}
