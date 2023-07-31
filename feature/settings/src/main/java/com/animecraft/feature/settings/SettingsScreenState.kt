package com.animecraft.feature.settings

import com.anime.animecraft.core.android.Event

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

data class SettingsScreenState(
    val darkModeEnabled: Boolean = false,
    val ratingDialogShown: Event<Boolean> = Event(false),
    val downloadDialogDisabled: Boolean = false
)
