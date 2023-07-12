package com.anime.animecraft.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.anime.animecraft.database.entity.SkinEntity

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Dao
interface SkinsDao {

    @Query("SELECT (SELECT COUNT(id) FROM skin) == 0")
    fun areSkinsEmpty(): Boolean

    @Query("SELECT * FROM skin WHERE id = :id")
    fun getSkinFlow(id: Int): Flow<SkinEntity>

    @Query("SELECT * FROM skin WHERE id = :id")
    fun getSkin(id: Int): SkinEntity

    @Query("SELECT * FROM skin")
    fun getAllSkins(): List<SkinEntity>

    @Query("SELECT * FROM skin")
    fun getAllSkinsFlow(): Flow<List<SkinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkin(skin: SkinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkins(skins: List<SkinEntity>)
}
