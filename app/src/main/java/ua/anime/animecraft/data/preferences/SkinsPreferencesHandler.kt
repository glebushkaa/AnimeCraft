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
            SKINS_PREFERENCES_NAME, Context.MODE_PRIVATE
        )
    }

    fun putBoolean(key: String, value: Boolean) {
        skinsPreferences?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String) = skinsPreferences?.getBoolean(key, false) ?: false

    companion object {
        private const val SKINS_PREFERENCES_NAME = "skins_preferences"

        const val IS_DOWNLOAD_DIALOG_DISABLED = "is_download_dialog_disabled"
    }
}