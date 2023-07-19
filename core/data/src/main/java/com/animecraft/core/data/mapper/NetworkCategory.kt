package com.animecraft.core.data.mapper

import com.animecraft.core.network.api.model.NetworkCategory
import com.anime.animecraft.database.entity.CategoryEntity

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 6/4/2023
 */

fun NetworkCategory.toCategoryEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name
)