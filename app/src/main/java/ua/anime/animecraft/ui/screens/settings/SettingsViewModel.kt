package ua.anime.animecraft.ui.screens.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ua.anime.animecraft.core.android.AnimeCraftViewModel
import ua.anime.animecraft.core.android.extensions.languagesList
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_DARK_MODE_ENABLED
import ua.anime.animecraft.data.preferences.SkinsPreferencesHandler.Companion.IS_DOWNLOAD_DIALOG_DISABLED
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

    var initialLanguage: Language = languagesList[0]
        private set

    val isDownloadDialogDisabled = skinsPreferencesHandler.getBoolean(
        IS_DOWNLOAD_DIALOG_DISABLED
    ) ?: false

    fun changeDarkMode(isDarkModeEnabled: Boolean) {
        updateDarkModeState(isDarkModeEnabled)
        skinsPreferencesHandler.putBoolean(IS_DARK_MODE_ENABLED, isDarkModeEnabled)
    }

    fun isDarkModeEnabled(
        isSystemInDarkModeByDefault: Boolean
    ): Boolean {
        val darkModePrefExist = skinsPreferencesHandler.checkValueExistence(IS_DARK_MODE_ENABLED)
        return if (!darkModePrefExist) {
            isSystemInDarkModeByDefault
        } else {
            skinsPreferencesHandler.getBoolean(IS_DARK_MODE_ENABLED)
        } == true
    }

    fun updateLanguagePreference(language: String) {
        skinsPreferencesHandler.putString(SELECTED_LANGUAGE, language)
    }

    fun getSelectedLanguage(languageLocale: String): Language {
        val languagePref = skinsPreferencesHandler.getString(SELECTED_LANGUAGE)
        initialLanguage = languagesList.find { it.languageLocale == languagePref } ?: run {
            languagesList.find { it.languageLocale == languageLocale } ?: languagesList[0]
        }
        return initialLanguage
    }

    fun updateDownloadDialogSetting(value: Boolean) {
        skinsPreferencesHandler.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, value)
    }
}
