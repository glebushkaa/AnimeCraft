package com.anime.animecraft.core.network.impl.model

import androidx.annotation.Keep

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 7/9/2023.
 */

@Keep
data class FirebaseSkin(
    val id: Int = 0,
    val name: String? = null,
    val categoryId: Int? = null
)