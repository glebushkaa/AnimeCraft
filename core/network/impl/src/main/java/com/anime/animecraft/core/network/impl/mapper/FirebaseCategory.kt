package com.anime.animecraft.core.network.impl.mapper

import com.anime.animecraft.core.network.impl.model.FirebaseCategory
import com.animecraft.core.network.api.model.NetworkCategory

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */
fun FirebaseCategory.toNetworkCategory(): NetworkCategory {
    return NetworkCategory(
        id = id ?: 0,
        name = name ?: ""
    )
}
