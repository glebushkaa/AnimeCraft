package ua.anime.animecraft.data.mapper

import ua.animecraft.database.entity.SkinEntity
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun ua.animecraft.database.entity.SkinEntity.to() = Skin(
    id = id,
    name = name,
    gameImageFileName = gameImageFileName,
    previewImageUrl = previewImageFileName,
    favorite = favorite,
    categoryId = categoryId
)

fun List<ua.animecraft.database.entity.SkinEntity>.to(): List<Skin> = map { it.to() }
