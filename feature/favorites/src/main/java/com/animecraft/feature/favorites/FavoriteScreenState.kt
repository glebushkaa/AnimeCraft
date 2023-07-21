package com.animecraft.feature.favorites

import com.anime.animecraft.core.android.Event
import com.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/11/2023
 */

data class FavoriteScreenState(
    val favorites: List<Skin> = listOf(),
    val downloadState: Event<Boolean>? = null,
    val searchInput: String = "",
    val downloadDialogShown: Event<Boolean> = Event(false)
)
