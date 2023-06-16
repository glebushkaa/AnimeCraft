package ua.anime.animecraft.data.network.model

import androidx.annotation.Keep

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Keep
data class NetworkSkin(
    val id: Int = 0,
    val name: String? = null,
    val category: Int? = null
)
