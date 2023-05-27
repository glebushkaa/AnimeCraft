package ua.anime.animecraft.data.mapper

import ua.anime.animecraft.data.database.entity.SkinEntity
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun SkinEntity.to() = Skin(
    id = id,
    name = name,
    gameImageUrl = gameImageUrl,
    previewImageUrl = previewImageUrl,
    favorite = favorite
)

fun List<SkinEntity>.to(): List<Skin> = map { it.to() }
