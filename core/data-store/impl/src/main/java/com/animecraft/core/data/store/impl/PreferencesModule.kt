package com.animecraft.core.data.store.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.animecraft.core.data.store.api.DialogsPreferencesApi
import com.animecraft.core.data.store.api.SettingsPreferencesApi
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    private const val SETTINGS_PREFERENCES_NAME = "settings_preferences"
    private const val SETTINGS_PREFERENCES = "settings_datastore"

    private const val DIALOGS_PREFERENCES_NAME = "dialogs_preferences"
    private const val DIALOGS_PREFERENCES = "dialogs_datastore"

    @Provides
    @Singleton
    fun provideSettingsPreferencesApi(
        @Named(SETTINGS_PREFERENCES) dataStore: DataStore<Preferences>
    ): SettingsPreferencesApi {
        return SettingsPreferencesApiImpl(dataStore)
    }

    @Provides
    @Singleton
    @Named(SETTINGS_PREFERENCES)
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(SETTINGS_PREFERENCES_NAME) }
        )
    }

    @Provides
    @Singleton
    fun provideDialogsPreferencesApi(
        @Named(DIALOGS_PREFERENCES) dataStore: DataStore<Preferences>
    ): DialogsPreferencesApi {
        return DialogsPreferencesApiImpl(dataStore)
    }

    @Provides
    @Singleton
    @Named(DIALOGS_PREFERENCES)
    fun provideDialogsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DIALOGS_PREFERENCES_NAME) }
        )
    }
}