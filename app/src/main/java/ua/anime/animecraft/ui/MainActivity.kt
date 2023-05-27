@file:Suppress("FunctionName")

package ua.anime.animecraft.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.anime.animecraft.ui.navigation.AnimeCraftHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeCraftApp()
        }
    }

    @Composable
    fun AnimeCraftApp() {
        val navController = rememberNavController()
        AnimeCraftHost(navController = navController)
    }
}
