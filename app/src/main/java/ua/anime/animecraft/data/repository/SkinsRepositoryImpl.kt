package ua.anime.animecraft.data.repository

import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ua.anime.animecraft.data.database.dao.SkinsDao
import ua.anime.animecraft.data.database.entity.SkinEntity
import ua.anime.animecraft.data.mapper.to
import ua.anime.animecraft.data.network.RealtimeSkinsApi
import ua.anime.animecraft.data.network.StorageSkinsApi
import ua.anime.animecraft.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

class SkinsRepositoryImpl @Inject constructor(
    private val realtimeSkinsApi: RealtimeSkinsApi,
    private val storageSkinsApi: StorageSkinsApi,
    private val skinsDao: SkinsDao
) : SkinsRepository {

    private val skins = mutableMapOf<Int, Skin>()

    override suspend fun getSkinsGameImageUrl(id: Int) = storageSkinsApi.getGameImageUrl(id)

    override suspend fun getSkinsGameFileName(id: Int) = skins[id]?.gameImageFileName

    override suspend fun getSkins(): List<Skin> {
        return skinsDao.getAllSkins().map(SkinEntity::to)
    }

    override suspend fun getSkin(id: Int): Skin {
        return skinsDao.getSkin(id).to()
    }

    override suspend fun getSkinsFlow(): Flow<List<Skin>> {
        return skinsDao.getAllSkinsFlow().map(List<SkinEntity>::to).onEach {
            it.forEach { skin -> skins[skin.id] = skin }
        }
    }

    override suspend fun getSkinFlow(id: Int): Flow<Skin> {
        return skinsDao.getSkinFlow(id).map(SkinEntity::to)
    }

    override suspend fun updateLocalSkinsFromNetwork(
        gameFileNamesMap: Map<Int, String>
    ) = coroutineScope {
        val localSkins = skinsDao.getAllSkins()
        val networkSkins = realtimeSkinsApi.getAllSkins()
        val list = networkSkins.map {
            val previewImageUrl = async { storageSkinsApi.getPreviewImageUrl(it.id) }
            async {
                it.to(
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
