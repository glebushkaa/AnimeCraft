package com.animecraft.core.data.store.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.animecraft.core.data.store.api.SettingsPreferencesApi
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

class SettingsPreferencesApiImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsPreferencesApi {

    override val languageFlow: Flow<String?>
        get() = dataStore.data.map {
            it[stringPreferencesKey(SELECTED_LANGUAGE)]
        }

    override val darkModeEnabled: Flow<Boolean?>
        get() = dataStore.data.map {
            it[booleanPreferencesKey(DARK_MODE_ENABLED)]
        }

    override val timesAppOpened: Flow<Int>
        get() = dataStore.data.map {
            it[intPreferencesKey(TIMES_APP_OPENED)] ?: 0
        }

    override suspend fun updateLanguage(language: String) {
        dataStore.edit {
            it[stringPreferencesKey(SELECTED_LANGUAGE)] = language
        }
    }

    override suspend fun darkModePreferenceExist(): Boolean {
        return false
    }

    override suspend fun updateDarkMode(enabled: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(DARK_MODE_ENABLED)] = enabled
        }
    }

    override suspend fun updateTimesAppOpened(times: Int) {
        dataStore.edit {
            it[intPreferencesKey(TIMES_APP_OPENED)] = times
        }
    }

    private companion object {
        const val SELECTED_LANGUAGE = "selected_language"
        const val DARK_MODE_ENABLED = "dark_mode_enabled"
        const val TIMES_APP_OPENED = "times_app_opened"
    }
}