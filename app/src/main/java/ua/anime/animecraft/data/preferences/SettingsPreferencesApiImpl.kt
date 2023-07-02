package ua.anime.animecraft.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.anime.animecraft.domain.prefs.SettingsPreferencesApi
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

class SettingsPreferencesApiImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsPreferencesApi {

    private val settingsPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SKINS_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun getCurrentLanguage(): String? {
        return settingsPreferences.getString(SELECTED_LANGUAGE, null)
    }

    override fun setCurrentLanguage(language: String) {
        settingsPreferences.edit { putString(SELECTED_LANGUAGE, language) }
    }

    override fun isDarkModeSettingExist(): Boolean {
        return settingsPreferences.contains(IS_DARK_MODE_ENABLED)
    }

    override fun isDarkModeEnabled(): Boolean {
        return settingsPreferences.getBoolean(IS_DARK_MODE_ENABLED, false)
    }

    override fun setDarkModeEnabled(isEnabled: Boolean) {
        settingsPreferences.edit { putBoolean(IS_DARK_MODE_ENABLED, isEnabled) }
    }

    override fun getTimesAppOpened(): Int {
        return settingsPreferences.getInt(TIMES_APP_OPENED, 0)
    }

    override fun setTimesAppOpened(times: Int) {
        settingsPreferences.edit { putInt(TIMES_APP_OPENED, times) }
    }

    private companion object {
        private const val SKINS_PREFERENCES_NAME = "skins_preferences"

        const val SELECTED_LANGUAGE = "selected_language"
        const val IS_DARK_MODE_ENABLED = "is_dark_mode_enabled"
        const val TIMES_APP_OPENED = "times_app_opened"
    }
}
