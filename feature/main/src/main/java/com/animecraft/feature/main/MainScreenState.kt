package com.animecraft.feature.main

import androidx.compose.runtime.Immutable
import com.anime.animecraft.core.android.Event
import com.animecraft.model.Category
import com.animecraft.model.Skin
import com.google.android.material.bottomsheet.BottomSheetBehavior.State

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

@Immutable
data class MainScreenState(
    val skins: List<Skin> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: Category? = null,
    val searchInput: String = "",
    val downloadDialogShown: Event<Boolean> = Event(false),
    val rateDialogShown: Event<Boolean> = Event(false),
    val downloadState: Event<Boolean>? = null
)