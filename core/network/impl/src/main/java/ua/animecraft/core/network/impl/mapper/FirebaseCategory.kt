package ua.animecraft.core.network.impl.mapper

import ua.animecraft.core.network.api.model.NetworkCategory
import ua.animecraft.core.network.impl.model.FirebaseCategory


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */
 
fun FirebaseCategory.toNetworkCategory(): NetworkCategory {
    return NetworkCategory(
        id = id ?: 0,
        name = name ?: ""
    )
}