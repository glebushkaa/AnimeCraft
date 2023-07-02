package ua.animecraft.core.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.animecraft.core.data.mapper.to
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

class FavoriteRepositoryImpl @Inject constructor(
    private val favoritesDao: ua.animecraft.database.dao.FavoritesDao
) : FavoritesRepository {

    override suspend fun getFavoritesSkins(): Flow<List<Skin>> {
        return favoritesDao.getFavoriteSkins().map(List<ua.animecraft.database.entity.SkinEntity>::to)
    }

    override suspend fun updateSkinFavoriteState(id: Int, favorite: Boolean) {
        favoritesDao.updateSkinFavoriteState(id, favorite)
    }
}
