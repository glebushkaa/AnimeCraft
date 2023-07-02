package ua.animecraft.core.data.mapper

import ua.anime.animecraft.data.database.entity.CategoryEntity
import ua.anime.animecraft.data.network.model.NetworkCategory
import ua.animecraft.database.entity.CategoryEntity

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

fun NetworkCategory.to(): CategoryEntity = CategoryEntity(
    id = id ?: 0,
    name = name ?: "Category"
)
