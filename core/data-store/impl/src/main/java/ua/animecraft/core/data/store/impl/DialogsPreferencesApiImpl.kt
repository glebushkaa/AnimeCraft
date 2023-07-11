package ua.animecraft.core.data.store.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.animecraft.core.data.store.api.DialogsPreferencesApi
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

class DialogsPreferencesApiImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DialogsPreferencesApi {

    override val downloadDialogDisabledFlow: Flow<Boolean>
        get() = dataStore.data.map { prefs ->
            prefs[booleanPreferencesKey(DOWNLOAD_DIALOG_DISABLED)] ?: false
        }

    override val rateDialogDisabledFlow: Flow<Boolean>
        get() = dataStore.data.map { prefs ->
            prefs[booleanPreferencesKey(RATE_DIALOG_DISABLED)] ?: false
        }

    override val rateCompletedFlow: Flow<Boolean>
        get() = dataStore.data.map { prefs ->
            prefs[booleanPreferencesKey(RATE_COMPLETED)] ?: false
        }

    override suspend fun updateDownloadDialogDisabled(isDisabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(DOWNLOAD_DIALOG_DISABLED)] = isDisabled
        }
    }

    override suspend fun updateRateDialogDisabled(isDisabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(RATE_DIALOG_DISABLED)] = isDisabled
        }
    }

    override suspend fun updateRateCompleted(isCompleted: Boolean) {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(RATE_COMPLETED)] = isCompleted
        }
    }

    private companion object {
        const val DOWNLOAD_DIALOG_DISABLED = "download_dialog_disabled"
        const val RATE_DIALOG_DISABLED = "rate_dialog_disabled"
        const val RATE_COMPLETED = "rate_completed"
    }
}