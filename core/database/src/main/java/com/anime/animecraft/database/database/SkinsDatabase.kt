package com.anime.animecraft.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anime.animecraft.database.dao.CategoryDao
import com.anime.animecraft.database.dao.FavoritesDao
import com.anime.animecraft.database.dao.SkinsDao
import com.anime.animecraft.database.database.SkinsDatabase.Companion.SKINS_DATABASE_VERSION
import com.anime.animecraft.database.entity.CategoryEntity
import com.anime.animecraft.database.entity.SkinEntity

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Database(
    entities = [SkinEntity::class, CategoryEntity::class],
    version = SKINS_DATABASE_VERSION,
    exportSchema = true
)
abstract class SkinsDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoritesDao

    abstract fun skinsDao(): SkinsDao

    abstract fun categoryDao(): CategoryDao

    companion object {
        const val SKINS_DATABASE_VERSION = 1
        const val SKINS_DATABASE_NAME = "skins_database"
    }
}
