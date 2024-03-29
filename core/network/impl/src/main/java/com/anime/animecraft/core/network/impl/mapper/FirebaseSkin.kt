package com.anime.animecraft.core.network.impl.mapper

import com.anime.animecraft.core.network.impl.model.FirebaseSkin
import com.animecraft.core.network.api.model.NetworkSkin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

fun FirebaseSkin.toNetworkSkin(): NetworkSkin {
    return NetworkSkin(
        id = id,
        name = name ?: "",
        category = category ?: 0
    )
}
