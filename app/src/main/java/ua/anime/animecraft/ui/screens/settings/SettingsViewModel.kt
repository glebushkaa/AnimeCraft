package ua.anime.animecraft.ui.screens.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.core.android.extensions.languagesList
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_DARK_MODE_ENABLED
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.SELECTED_LANGUAGE
import ua.anime.animecraft.ui.model.Language
import ua.anime.animecraft.ui.utils.DarkModeHandler.updateDarkModeState

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val skinsPreferencesHandler: SkinsPreferencesHandler
) : AnimeCraftViewModel() {

    var initialLanguage: Language? = languagesList[0]
        private set

    fun changeDarkMode(isDarkModeEnabled: Boolean) {
        updateDarkModeState(isDarkModeEnabled)
        skinsPreferencesHandler.putBoolean(IS_DARK_MODE_ENABLED, isDarkModeEnabled)
    }

    fun isDarkModeEnabled() = skinsPreferencesHandler.getBoolean(IS_DARK_MODE_ENABLED)
    fun updateLanguagePreference(language: String) {
        skinsPreferencesHandler.putString(SELECTED_LANGUAGE, language)
    }

    fun getSelectedLanguage(): Language {
        val languagePref = skinsPreferencesHandler.getString(SELECTED_LANGUAGE)
        initialLanguage = languagesList.find { it.languageLocale == languagePref }
        return initialLanguage ?: languagesList[0]
    }
}
