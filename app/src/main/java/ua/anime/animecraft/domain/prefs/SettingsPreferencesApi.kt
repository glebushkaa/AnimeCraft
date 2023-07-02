package ua.anime.animecraft.domain.prefs

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

interface SettingsPreferencesApi {

    fun getCurrentLanguage(): String?

    fun setCurrentLanguage(language: String)

    fun isDarkModeSettingExist(): Boolean

    fun isDarkModeEnabled(): Boolean

    fun setDarkModeEnabled(isEnabled: Boolean)

    fun getTimesAppOpened(): Int

    fun setTimesAppOpened(times: Int)
}