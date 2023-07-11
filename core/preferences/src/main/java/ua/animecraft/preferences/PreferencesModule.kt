package ua.animecraft.preferences

import com.animecraft.core.domain.prefs.DialogsPreferencesApi
import com.animecraft.core.domain.prefs.SettingsPreferencesApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/1/2023
 */

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesModule {

    @Binds
    @Singleton
    fun provideSkinsPreferencesApi(
        settingsPreferencesApiImpl: SettingsPreferencesApiImpl
    ): SettingsPreferencesApi

    @Binds
    @Singleton
    fun provideDialogsPreferencesApi(
        dialogsPreferencesApiImpl: DialogsPreferencesApiImpl
    ): DialogsPreferencesApi
}