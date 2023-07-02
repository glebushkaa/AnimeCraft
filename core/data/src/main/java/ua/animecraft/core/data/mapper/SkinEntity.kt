package ua.animecraft.core.data.mapper

import ua.animecraft.database.entity.SkinEntity
import ua.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun SkinEntity.toSkin() = Skin(
    id = id,
    name = name,
    gameImageFileName = gameImageFileName,
    previewImageUrl = previewImageFileName,
    favorite = favorite,
    categoryId = categoryId
)
