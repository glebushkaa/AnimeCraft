package ua.anime.animecraft.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

object DarkModeHandler {

    private val _darkModeState = MutableStateFlow(false)
    val darkModeState = _darkModeState.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    fun updateDarkModeState(value: Boolean) {
        scope.launch { _darkModeState.emit(value) }
    }
}
