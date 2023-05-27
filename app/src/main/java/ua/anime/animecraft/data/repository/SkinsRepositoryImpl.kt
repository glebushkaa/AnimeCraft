package ua.anime.animecraft.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.anime.animecraft.data.database.dao.SkinsDao
import ua.anime.animecraft.data.database.entity.SkinEntity
import ua.anime.animecraft.data.network.RealtimeSkinsApi
import ua.anime.animecraft.data.network.StorageSkinsApi
import ua.anime.animecraft.data.network.model.NetworkSkin
import ua.anime.animecraft.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.model.Skin
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

class SkinsRepositoryImpl @Inject constructor(
    private val realtimeSkinsApi: RealtimeSkinsApi,
    private val storageSkinsApi: StorageSkinsApi,
    private val skinsDao: SkinsDao
) : SkinsRepository {

    override suspend fun getSkinsFlow(): Flow<List<Skin>> {
        return skinsDao.getAllSkinsFlow().map(List<SkinEntity>::to)
    }

    override suspend fun updateLocalSkinsFromNetwork() = coroutineScope {
        val localSkins = skinsDao.getAllSkins()
        val networkSkins = realtimeSkinsApi.getAllSkins()
        val list = networkSkins.map {
            val previewImageUrl = async { storageSkinsApi.getPreviewImageUrl(it.id) }
            val gameImageUrl = async { storageSkinsApi.getGameImageUrl(it.id) }
            it.to(
                gameUrl = gameImageUrl.await(),
                previewUrl = previewImageUrl.await(),
                favorite = localSkins.find { localSkin -> localSkin.id == it.id }?.favorite ?: false
            )
        }
        skinsDao.insertSkins(list)
    }
}

private fun List<SkinEntity>.to() = map { it.to() }

private fun SkinEntity.to() = Skin(
    id = id,
    name = name,
    gameImageUrl = gameImageUrl,
    previewImageUrl = previewImageUrl,
    favorite = favorite
)

private fun NetworkSkin.to(
    gameUrl: String,
    previewUrl: String,
    favorite: Boolean
) = SkinEntity(
    id = id,
    name = name ?: "Skin",
    gameImageUrl = gameUrl,
    previewImageUrl = previewUrl,
    favorite = favorite
)