package ua.anime.animecraft.data.database.dao

import androidx.room.Dao
import androidx.room.Query


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Dao
interface FavoritesDao {

    @Query("UPDATE skin SET favorite = :favorite WHERE id = :id")
    fun updateFavoriteSkin(id: Int, favorite: Boolean)
}