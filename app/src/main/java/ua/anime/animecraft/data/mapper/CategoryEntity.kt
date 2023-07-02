package ua.anime.animecraft.data.mapper

import ua.animecraft.database.entity.CategoryEntity
import ua.anime.animecraft.ui.model.Category

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

fun ua.animecraft.database.entity.CategoryEntity.to(): Category = Category(
    id = id,
    name = name
)

fun List<ua.animecraft.database.entity.CategoryEntity>.to(): List<Category> = map { it.to() }
