package ua.anime.animecraft.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.anime.animecraft.data.database.entity.SkinEntity


/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 5/21/2023.
 */

@Dao
interface SkinsDao {

    @Query("SELECT * FROM skin")
    fun getAllSkins(): Flow<List<SkinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkin(skin: SkinEntity)
}