package com.animecraft.core.data.repository

import com.anime.animecraft.database.dao.SkinsDao
import com.animecraft.core.data.mapper.toSkin
import com.animecraft.core.data.mapper.toSkinEntity
import com.animecraft.core.data.mapper.toSkinsList
import com.animecraft.core.domain.repository.SkinsRepository
import com.animecraft.core.network.api.NetworkDatabaseApi
import com.animecraft.core.network.api.NetworkStorageApi
import com.animecraft.model.Skin
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

class SkinsRepositoryImpl @Inject constructor(
    private val networkDatabaseApi: NetworkDatabaseApi,
    private val networkStorageApi: NetworkStorageApi,
    private val skinsDao: SkinsDao
) : SkinsRepository {

    private val skins = mutableMapOf<Int, Skin>()

    override suspend fun getSkinsGameImageUrl(id: Int) = networkStorageApi.getGameImageUrl(id)

    override suspend fun getSkinsGameFileName(id: Int) = skins[id]?.gameImageFileName

    override suspend fun getSkins(): List<Skin> {
        return skinsDao.getAllSkins().map { it.toSkin() }
    }

    override suspend fun getSkin(id: Int): Skin {
        return skinsDao.getSkin(id).toSkin()
    }

    override suspend fun getSkinsFlow(): Flow<List<Skin>> {
        return skinsDao.getAllSkinsFlow().map {
            it.toSkinsList()
        }.onEach {
            it.forEach { skin -> skins[skin.id] = skin }
        }
    }

    override suspend fun getSkinFlow(id: Int): Flow<Skin> {
        return skinsDao.getSkinFlow(id).map {
            it.toSkin()
        }
    }

    override suspend fun updateLocalSkinsFromNetwork(
        gameFileNamesMap: Map<Int, String>
    ) = coroutineScope {
        val localSkins = skinsDao.getAllSkins()
        val networkSkins = networkDatabaseApi.getAllSkins()
        val list = networkSkins.map {
            val previewImageUrl = async { networkStorageApi.getPreviewImageUrl(it.id) }
            async {
                it.toSkinEntity(
                    gameUrl = gameFileNamesMap[it.id] ?: "",
                    previewUrl = previewImageUrl.await(),
                    favorite = localSkins.find { localSkin -> localSkin.id == it.id }?.favorite
                        ?: false
                )
            }
        }
        skinsDao.insertSkins(list.awaitAll())
    }
}
