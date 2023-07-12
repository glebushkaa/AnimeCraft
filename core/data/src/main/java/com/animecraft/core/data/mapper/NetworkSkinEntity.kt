package com.animecraft.core.data.mapper

import com.animecraft.core.network.api.model.NetworkSkin
import com.anime.animecraft.database.entity.SkinEntity

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

fun NetworkSkin.toSkinEntity(gameUrl: String, previewUrl: String, favorite: Boolean) = SkinEntity(
    id = id,
    name = name,
    gameImageFileName = gameUrl,
    previewImageFileName = previewUrl,
    favorite = favorite,
    categoryId = categoryId
)
