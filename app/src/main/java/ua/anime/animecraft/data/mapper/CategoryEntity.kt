package ua.anime.animecraft.data.mapper

import ua.anime.animecraft.data.database.entity.CategoryEntity
import ua.anime.animecraft.ui.model.Category

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

fun CategoryEntity.to(): Category = Category(
    id = id,
    name = name
)

fun List<CategoryEntity>.to(): List<Category> = map { it.to() }
