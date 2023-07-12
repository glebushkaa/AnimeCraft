package com.animecraft.core.data.store.api

import kotlinx.coroutines.flow.Flow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

interface SettingsPreferencesApi {

    val languageFlow: Flow<String?>
    val darkModeEnabled: Flow<Boolean?>
    val timesAppOpened: Flow<Int>

    suspend fun updateLanguage(language: String)

    suspend fun darkModePreferenceExist(): Boolean

    suspend fun updateDarkMode(enabled: Boolean)

    suspend fun updateTimesAppOpened(times: Int)
}