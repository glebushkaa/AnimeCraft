package com.animecraft.animecraft.common

import com.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */
fun <T> MutableList<T>.replaceAllElements(list: List<T>) {
    clear()
    addAll(list)
}

fun List<Skin>.filterListByName(name: String) = filter {
    val currentName = it.name.lowercase().trim()
    val currentSearchInput = name.lowercase().trim()
    currentName.startsWith(currentSearchInput) || currentName.contains(currentSearchInput)
}

fun List<Skin>.filterListByCategoryId(categoryId: Int) = filter {
    it.categoryId == categoryId
}

fun <T> List<T>.performIf(predicate: Boolean, action: List<T>.() -> List<T>): List<T> {
    return if (predicate) action(this) else this
}
