package ua.anime.animecraft.data.preferences

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.anime.animecraft.domain.prefs.DialogsPreferencesApi
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

class DialogsPreferencesApiImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DialogsPreferencesApi {

    private val dialogsPreferences by lazy {
        context.getSharedPreferences(DIALOGS_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun isDownloadDialogDisabled(): Boolean {
        return dialogsPreferences.getBoolean(IS_DOWNLOAD_DIALOG_DISABLED, false)
    }

    override fun setDownloadDialogDisabled(isDisabled: Boolean) {
        dialogsPreferences.edit { putBoolean(IS_DOWNLOAD_DIALOG_DISABLED, isDisabled) }
    }

    override fun isRateDialogDisabled(): Boolean {
        return dialogsPreferences.getBoolean(IS_RATE_DIALOG_DISABLED, false)
    }

    override fun setRateDialogDisabled(isDisabled: Boolean) {
        dialogsPreferences.edit { putBoolean(IS_RATE_DIALOG_DISABLED, isDisabled) }
    }

    override fun isRateCompleted(): Boolean {
        return dialogsPreferences.getBoolean(IS_RATE_COMPLETED, false)
    }

    override fun setRateCompleted(isCompleted: Boolean) {
        dialogsPreferences.edit { putBoolean(IS_RATE_COMPLETED, isCompleted) }
    }

    private companion object {
        private const val DIALOGS_PREFERENCES_NAME = "dialogs_preferences"

        const val IS_DOWNLOAD_DIALOG_DISABLED = "is_download_dialog_disabled"
        const val IS_RATE_DIALOG_DISABLED = "is_rate_mode_enabled"
        const val IS_RATE_COMPLETED = "is_rate_completed"
    }
}