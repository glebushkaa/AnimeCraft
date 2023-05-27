package ua.anime.animecraft.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.anime.animecraft.data.database.dao.FavoritesDao
import ua.anime.animecraft.data.database.entity.SkinEntity
import ua.anime.animecraft.data.mapper.to
import ua.anime.animecraft.domain.repository.FavoritesRepository
import ua.anime.animecraft.ui.model.Skin

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

class FavoriteRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override suspend fun getFavoritesSkins(): Flow<List<Skin>> {
        return favoritesDao.getFavoriteSkins().map(List<SkinEntity>::to)
    }

    override suspend fun updateFavoriteSkin(id: Int, favorite: Boolean) {
        favoritesDao.updateFavoriteSkin(id, favorite)
    }
}
