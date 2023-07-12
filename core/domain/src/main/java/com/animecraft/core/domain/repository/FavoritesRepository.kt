package com.animecraft.core.domain.repository

import kotlinx.coroutines.flow.Flow
import com.animecraft.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

interface FavoritesRepository {

    suspend fun getFavoritesSkins(): Flow<List<Skin>>

    suspend fun updateSkinFavoriteState(id: Int, favorite: Boolean)
}
