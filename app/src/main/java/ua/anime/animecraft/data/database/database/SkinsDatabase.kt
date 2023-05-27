package ua.anime.animecraft.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.anime.animecraft.data.database.SKINS_DATABASE_VERSION
import ua.anime.animecraft.data.database.dao.FavoritesDao
import ua.anime.animecraft.data.database.dao.SkinsDao
import ua.anime.animecraft.data.database.entity.SkinEntity


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Database(entities = [SkinEntity::class], version = SKINS_DATABASE_VERSION, exportSchema = true)
abstract class SkinsDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoritesDao

    abstract fun skinsDao(): SkinsDao
}