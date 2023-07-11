package ua.animecraft.feature.settings

import com.animecraft.core.android.AnimeCraftViewModel
import com.animecraft.core.android.extensions.languagesList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import ua.animecraft.data.store.SettingsPreferencesApiImpl
import ua.animecraft.data.store.SettingsPreferencesApiImpl.Companion.IS_DARK_MODE_ENABLED
import ua.animecraft.data.store.SettingsPreferencesApiImpl.Companion.IS_DOWNLOAD_DIALOG_DISABLED
import ua.animecraft.data.store.SettingsPreferencesApiImpl.Companion.SELECTED_LANGUAGE
import ua.anime.animecraft.ui.model.Language
import ua.anime.animecraft.ui.utils.DarkModeHandler.updateDarkModeState

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/30/2023
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsPreferencesApiImpl: ua.animecraft.data.store.SettingsPreferencesApiImpl
) : AnimeCraftViewModel() {

    var initialLanguage: Language = languagesList[0]
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
        initialLanguage = languagesList.find { it.languageLocale == languagePref } ?: run {
            languagesList.find { it.languageLocale == languageLocale } ?: languagesList[0]
        }
        return initialLanguage
    }

    fun updateDownloadDialogSetting(value: Boolean) {
        settingsPreferencesApiImpl.putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, value)
    }
}
