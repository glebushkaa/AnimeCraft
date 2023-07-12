package com.anime.animecraft.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.anime.animecraft.database.entity.SkinEntity

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Dao
interface FavoritesDao {

    @Query("UPDATE skin SET favorite = :favorite WHERE id = :id")
    fun updateSkinFavoriteState(id: Int, favorite: Boolean)

    @Query("SELECT * FROM skin WHERE favorite = 1")
    fun getFavoriteSkins(): Flow<List<SkinEntity>>
}
