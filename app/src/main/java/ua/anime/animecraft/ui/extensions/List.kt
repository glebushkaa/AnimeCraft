package ua.anime.animecraft.ui.extensions

import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun List<Skin>.filterListByName(name: String) = filter {
    val currentName = it.name.lowercase().trim()
    val currentSearchInput = name.lowercase().trim()
    currentName.startsWith(currentSearchInput) || currentName.contains(currentSearchInput)
}

fun List<Skin>.filterListByCategoryId(categoryId: Int) = filter {
    it.categoryId == categoryId
}
