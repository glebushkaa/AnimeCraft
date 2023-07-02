package ua.anime.animecraft.ui.screens.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.animecraft.core.common_android.android.AnimeCraftViewModel
import com.animecraft.core.common_android.android.extensions.languagesList
import ua.anime.animecraft.data.preferences.SettingsPreferencesApiImpl
import ua.anime.animecraft.data.preferences.SettingsPreferencesApiImpl.Companion.IS_DARK_MODE_ENABLED
import ua.anime.animecraft.data.preferences.SettingsPreferencesApiImpl.Companion.IS_DOWNLOAD_DIALOG_DISABLED
import ua.anime.animecraft.data.preferences.SettingsPreferencesApiImpl.Companion.SELECTED_LANGUAGE
import ua.anime.animecraft.ui.model.Language
import ua.anime.animecraft.ui.utils.DarkModeHandler.updateDarkModeState

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsPreferencesApiImpl: SettingsPreferencesApiImpl
) : com.animecraft.core.common_android.android.AnimeCraftViewModel() {

    var initialLanguage: Language = com.animecraft.core.common_android.android.extensions.languagesList[0]
        private set

    val isDownloadDialogDisabled = settingsPreferencesApiImpl.getBoolean(
        IS_DOWNLOAD_DIALOG_DISABLED
    ) ?: false

    fun changeDarkMode(isDarkModeEnabled: Boolean) {
        updateDarkModeState(isDarkModeEnabled)
        settingsPreferencesApiImpl.putBoolean(IS_DARK_MODE_ENABLED, isDarkModeEnabled)
    }

    fun isDarkModeEnabled(
        isSystemInDarkModeByDefault: Boolean
    ): Boolean {
        val darkModePrefExist = settingsPreferencesApiImpl.checkValueExistence(IS_DARK_MODE_ENABLED)
        return if (!darkModePrefExist) {
            isSystemInDarkModeByDefault
        } else {
            settingsPreferencesApiImpl.getBoolean(IS_DARK_MODE_ENABLED)
        } == true
    }

    fun updateLanguagePreference(language: String) {
        settingsPreferencesApiImpl.putString(SELECTED_LANGUAGE, language)
    }

    fun getSelectedLanguage(languageLocale: String): Language {
        val languagePref = settingsPreferencesApiImpl.getString(SELECTED_LANGUAGE)
        initialLanguage = com.animecraft.core.common_android.android.extensions.languagesList.find { it.languageLocale == languagePref } ?: run {
            com.animecraft.core.common_android.android.extensions.languagesList.find { it.languageLocale == languageLocale } ?: com.animecraft.core.common_android.android.extensions.languagesList[0]
        }
        return initialLanguage
    }

    fun updateDownloadDialogSetting(value: Boolean) {
        settingsPreferencesApiImpl.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, value)
    }
}
