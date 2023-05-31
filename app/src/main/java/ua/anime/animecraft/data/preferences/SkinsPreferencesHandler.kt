package ua.anime.animecraft.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/29/2023
 */

@Singleton
class SkinsPreferencesHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var skinsPreferences: SharedPreferences? = null

    init {
        initSkinsPreferences()
    }

    private fun initSkinsPreferences() {
        skinsPreferences = context.getSharedPreferences(
            SKINS_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun putBoolean(key: String, value: Boolean) {
        skinsPreferences?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String) = skinsPreferences?.getBoolean(key, false)

    fun putString(key: String, value: String) {
        skinsPreferences?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String) = skinsPreferences?.getString(key, "")

    fun getInt(key: String) = skinsPreferences?.getInt(key, 0)
    fun putInt(key: String, value: Int) = skinsPreferences?.edit()?.putInt(key, value)?.apply()

    companion object {
        private const val SKINS_PREFERENCES_NAME = "skins_preferences"

        const val IS_DOWNLOAD_DIALOG_DISABLED = "is_download_dialog_disabled"
        const val SELECTED_LANGUAGE = "selected_language"
        const val IS_DARK_MODE_ENABLED = "is_dark_mode_enabled"
        const val IS_RATE_DIALOG_DISABLED = "is_rate_mode_enabled"
        const val TIMES_APP_OPENED = "times_app_opened"
        const val IS_RATE_COMPLETED = "is_rate_completed"
    }
}
