package com.animecraft.core.data.mapper

import com.anime.animecraft.database.entity.SkinEntity
import com.animecraft.model.Skin

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

fun List<SkinEntity>.toSkinsList() = map {
    it.toSkin()
}
