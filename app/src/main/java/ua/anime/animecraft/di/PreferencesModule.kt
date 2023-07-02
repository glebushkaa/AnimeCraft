package ua.anime.animecraft.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.anime.animecraft.data.preferences.DialogsPreferencesApiImpl
import ua.anime.animecraft.data.preferences.SettingsPreferencesApiImpl
import ua.anime.animecraft.domain.prefs.DialogsPreferencesApi
import ua.anime.animecraft.domain.prefs.SettingsPreferencesApi
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