package ua.animecraft.feature.main

import com.animecraft.core.android.Event
import ua.animecraft.model.Category
import ua.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

data class MainScreenState(
    val skins: List<Skin> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: Category? = null,
    val searchInput: String = "",
    val downloadDialogShown: Event<Boolean> = Event(false),
    val rateDialogShown: Event<Boolean> = Event(false),
    val downloadState: Event<Boolean> = Event(false)
)