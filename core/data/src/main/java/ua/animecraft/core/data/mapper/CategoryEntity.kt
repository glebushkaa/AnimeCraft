package ua.animecraft.core.data.mapper

import ua.animecraft.database.entity.CategoryEntity
import ua.animecraft.model.Category

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

fun CategoryEntity.toCategory(): Category = Category(
    id = id,
    name = name
)
