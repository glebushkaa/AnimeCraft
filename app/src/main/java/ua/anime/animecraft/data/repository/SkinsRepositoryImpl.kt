package ua.anime.animecraft.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ua.anime.animecraft.data.network.RealtimeSkinsApi
import ua.anime.animecraft.data.network.StorageSkinsApi
import ua.anime.animecraft.domain.repository.SkinsRepository
import ua.anime.animecraft.ui.model.Skin
import javax.inject.Inject


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

class SkinsRepositoryImpl @Inject constructor(
    private val realtimeSkinsApi: RealtimeSkinsApi,
    private val storageSkinsApi: StorageSkinsApi
) : SkinsRepository {

    override suspend fun getSkins(): List<Skin> = coroutineScope {
        realtimeSkinsApi.getAllSkins().map {
            val previewImageUrl = async { storageSkinsApi.getPreviewImageUrl(it.id) }
            val gameImageUrl = async { storageSkinsApi.getGameImageUrl(it.id) }
            Skin(
                id = it.id,
                name = it.name ?: "Skin",
                gameImageUrl = gameImageUrl.await(),
                previewImageUrl = previewImageUrl.await()
            )
        }
    }

    override suspend fun changeSkinFavorite(favorite: Boolean) {
        TODO("Not yet implemented")
    }
}