package com.animecraft.core.domain.repository

import com.animecraft.model.Skin
import kotlinx.coroutines.flow.Flow

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

interface FavoritesRepository {

    suspend fun getFavoritesSkins(): Flow<List<Skin>>

    suspend fun updateSkinFavoriteState(id: Int, favorite: Boolean)
}
