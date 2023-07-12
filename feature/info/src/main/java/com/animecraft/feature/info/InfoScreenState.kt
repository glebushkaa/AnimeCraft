package com.animecraft.feature.info

import com.anime.animecraft.core.android.Event
import com.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

data class InfoScreenState(
    val skin: Skin? = null,
    val categoryName: String? = null,
    val categoryVisible: Boolean = false,
    val downloadState: Event<Boolean> = Event(false),
    val downloadDialogShown: Event<Boolean> = Event(false),
    val loading: Boolean = false
)