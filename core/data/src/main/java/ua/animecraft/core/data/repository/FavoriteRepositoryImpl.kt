package ua.animecraft.core.data.repository

import com.animecraft.core.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.animecraft.core.data.mapper.toSkinsList
import ua.animecraft.database.dao.FavoritesDao
import ua.animecraft.model.Skin
import javax.inject.Inject

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/27/2023.
 */

class FavoriteRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override suspend fun getFavoritesSkins(): Flow<List<Skin>> {
        return favoritesDao.getFavoriteSkins().map {
            it.toSkinsList()
        }
    }

    override suspend fun updateSkinFavoriteState(id: Int, favorite: Boolean) {
        favoritesDao.updateSkinFavoriteState(id, favorite)
    }
}
