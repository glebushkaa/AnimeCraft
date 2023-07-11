package ua.animecraft.core.network.impl.mapper

import ua.animecraft.core.network.api.model.NetworkSkin
import ua.animecraft.core.network.impl.model.FirebaseSkin


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

fun FirebaseSkin.toNetworkSkin(): NetworkSkin {
    return NetworkSkin(
        id = id,
        name = name ?: "",
        categoryId = categoryId ?: 0,
    )
}