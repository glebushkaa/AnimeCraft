package ua.anime.animecraft.data.mapper

import ua.anime.animecraft.data.database.entity.SkinEntity
import ua.anime.animecraft.data.network.model.NetworkSkin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun NetworkSkin.to(gameUrl: String, previewUrl: String, favorite: Boolean) = SkinEntity(
    id = id,
    name = name ?: "Skin",
    gameImageFileName = gameUrl,
    previewImageFileName = previewUrl,
    favorite = favorite
)
