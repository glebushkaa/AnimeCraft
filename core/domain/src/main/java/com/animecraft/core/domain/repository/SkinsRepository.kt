package com.animecraft.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

interface SkinsRepository {

    suspend fun getSkinsGameImageUrl(id: Int): String?

    suspend fun getSkinsGameFileName(id: Int): String?

    suspend fun getSkinsFlow(): Flow<List<Skin>>
    suspend fun getSkins(): List<Skin>

    suspend fun getSkinFlow(id: Int): Flow<Skin>

    suspend fun getSkin(id: Int): Skin

    suspend fun updateLocalSkinsFromNetwork(gameFileNamesMap: Map<Int, String>)
}
